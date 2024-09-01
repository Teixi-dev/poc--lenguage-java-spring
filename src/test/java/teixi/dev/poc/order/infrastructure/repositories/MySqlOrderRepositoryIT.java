package teixi.dev.poc.order.infrastructure.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.order.domain.exceptions.InvalidOrderStatusException;
import teixi.dev.poc.order.domain.exceptions.OrderNotFoundException;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderCode;
import teixi.dev.poc.order.domain.models.OrderStatus;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MySqlOrderRepositoryIT extends IntegrationTestWithApplicationContainers {
    @Autowired
    private MySqlOrderRepository repository;
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Order SOME_ORDER = Order.builder()
            .code(OrderCode.builder()
                    .value(UUID.fromString("c5e6d34e-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .clientCode(ClientCode.builder()
                    .value(UUID.fromString("b4e5c42e-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .productCode(ProductCode.builder()
                    .value(UUID.fromString("a3e5b2e8-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .amount(1)
            .status(OrderStatus.PENDING)
            .createdAt(fromString("2014-05-21 13:30:10"))
            .updatedAt(fromString("2014-05-28 15:30:10"))
            .build();
    private static final Order OTHER_ORDER = Order.builder()
            .code(OrderCode.builder()
                    .value(UUID.fromString("c5e6d66c-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .clientCode(ClientCode.builder()
                    .value(UUID.fromString("b4e5c42e-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .productCode(ProductCode.builder()
                    .value(UUID.fromString("a3e5b8e6-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .amount(2)
            .status(OrderStatus.SHIPPED)
            .createdAt(fromString("2014-05-21 10:30:10"))
            .updatedAt(fromString("2014-05-24 10:30:10"))
            .build();
    private static final Order OTHER_MORE_ORDER = Order.builder()
            .code(OrderCode.builder()
                    .value(UUID.fromString("c5e6d9b8-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .clientCode(ClientCode.builder()
                    .value(UUID.fromString("b4e5c9ec-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .productCode(ProductCode.builder()
                    .value(UUID.fromString("a3e5bc1a-7e6b-11ec-90d6-0242ac120003"))
                    .build()
            )
            .amount(1)
            .status(OrderStatus.DELIVERED)
            .createdAt(fromString("2014-05-23 11:30:10"))
            .updatedAt(fromString("2014-05-24 12:30:10"))
            .build();
    private static final OrderCode ORDER_WITH_INVALID_STATUS = OrderCode.builder()
            .value(UUID.fromString("9741e2a5-53ab-4cc2-8445-dc241bea5a6d"))
            .build();

    @Test
    public void whenFindOrderByOrderCodeAndExistReturnOrder() {
        Order result = this.repository.find(SOME_ORDER.getCode());

        Assertions.assertEquals(SOME_ORDER, result);
    }

    @Test
    public void whenFindOrderByOrderCodeButNotExistThenThrowOrderNotFoundException() {
        Assertions.assertThrows(
                OrderNotFoundException.class,
                () -> this.repository.find(OrderCode.create())
        );
    }

    @Test
    public void whenSearchByClientCodeAndHaveOrdersThenReturnOrderList() {
        List<Order> expectedOrders = List.of(SOME_ORDER, OTHER_ORDER);

        List<Order> result = this.repository.searchByClientCode(SOME_ORDER.getClientCode());

        Assertions.assertEquals(expectedOrders, result);
    }

    @Test
    public void whenSearchByClientCodeButNotHaveOrdersThenReturnEmptyList() {
        List<Order> result = this.repository.searchByClientCode(ClientCode.create());

        Assertions.assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void whenSaveOrderAndExistUpdateItAndPersist() throws IOException {
        Order order = this.repository.find(SOME_ORDER.getCode());

        Order advancedOrder = order.advanceStatus();

        this.repository.save(advancedOrder);

        Order result = this.repository.find(SOME_ORDER.getCode());

        Assertions.assertEquals(advancedOrder, result);

        this.restartDatabase();
    }

    @Test
    public void whenSaveOrderAndNotExistCreateAndPersist() throws IOException {
        Order newOrder = Order.create(
                SOME_ORDER.getClientCode(),
                SOME_ORDER.getProductCode(),
                2
        );

        this.repository.save(newOrder);

        Order result = this.repository.find(newOrder.getCode());

        Assertions.assertEquals(newOrder, result);

        this.restartDatabase();
    }

    @Test
    public void whenFindOrderWithInvalidStatusThenThrowInvalidOrderStatusException() throws IOException {
        Assertions.assertThrows(
                InvalidOrderStatusException.class,
                () -> this.repository.find(ORDER_WITH_INVALID_STATUS)
        );

        this.restartDatabase();
    }

    private static Date fromString(String dateString) {
        try {
            return FORMAT_DATE.parse(dateString);
        } catch (ParseException e) {
            throw new InvalidParameterException(e);
        }
    }
}
