package teixi.dev.poc.order.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.order.domain.exceptions.InvalidOrderAmountException;
import teixi.dev.poc.order.domain.exceptions.OrderAdvanceStatusException;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.shared.infrastructure.services.TimeStampGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Getter
@Builder
public class Order {
    private static final int MIN_AMOUNT = 1;
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private OrderCode code;
    private ClientCode clientCode;
    private ProductCode productCode;
    private int amount;
    private OrderStatus status;
    private Date updatedAt;
    private Date createdAt;

    public Order advanceStatus() throws OrderAdvanceStatusException {
        Order advancedOrder = Order.builder()
                .code(this.code)
                .clientCode(this.clientCode)
                .productCode(this.productCode)
                .amount(this.amount)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(TimeStampGenerator.now())
                .build();

        switch (advancedOrder.status) {
            case PENDING -> advancedOrder.status = OrderStatus.SHIPPED;
            case SHIPPED -> advancedOrder.status = OrderStatus.DELIVERED;
            default -> throw new OrderAdvanceStatusException(advancedOrder.code, advancedOrder.status);
        }
        return advancedOrder;
    }

    public static Order create(ClientCode clientCode, ProductCode productCode, int amount) {
        if (amount < MIN_AMOUNT)
            throw new InvalidOrderAmountException();

        Date nowDate = TimeStampGenerator.now();

        return Order.builder()
                .code(OrderCode.create())
                .clientCode(clientCode)
                .productCode(productCode)
                .amount(amount)
                .status(OrderStatus.PENDING)
                .createdAt(nowDate)
                .updatedAt(nowDate)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return amount == order.amount &&
                Objects.equals(code, order.code) &&
                Objects.equals(clientCode, order.clientCode) &&
                Objects.equals(productCode, order.productCode) &&
                status == order.status &&
                Objects.equals(updatedAt, order.updatedAt) &&
                Objects.equals(createdAt, order.createdAt);
    }
}
