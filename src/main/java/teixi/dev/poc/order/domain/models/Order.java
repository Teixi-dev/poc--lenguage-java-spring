package teixi.dev.poc.order.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.order.domain.exceptions.InvalidAmountException;
import teixi.dev.poc.order.domain.exceptions.OrderAdvanceStatusException;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.product.domain.models.ProductCode;

import java.util.Date;

@Getter
@Builder
public class Order {
    private static final int MIN_AMOUNT = 1;

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
                .updatedAt(this.updatedAt)
                .build();

        switch (advancedOrder.status) {
            case PENDING -> advancedOrder.status = OrderStatus.SHIPPED;
            case SHIPPED -> advancedOrder.status = OrderStatus.DELIVERED;
            default -> throw new OrderAdvanceStatusException(advancedOrder.code, advancedOrder.status);
        }
        return advancedOrder;
    }

    public static Order create(OrderCode orderCode, ClientCode clientCode, ProductCode productCode, int amount) {
        if (amount < MIN_AMOUNT)
            throw new InvalidAmountException();

        Date nowDate = new Date();

        return Order.builder()
                .code(orderCode)
                .clientCode(clientCode)
                .productCode(productCode)
                .amount(amount)
                .status(OrderStatus.PENDING)
                .createdAt(nowDate)
                .updatedAt(nowDate)
                .build();
    }
}
