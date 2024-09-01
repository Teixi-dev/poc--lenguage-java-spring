package teixi.dev.poc.order.domain.models;

import net.datafaker.Faker;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;

import java.util.Date;
import java.util.List;

public class OrderTestDataGenerator {
    private final Faker faker = new Faker();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();
    public final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();

    public Order generateOrder(
            OrderCode orderCode,
            ClientCode clientCode,
            ProductCode productCode,
            OrderStatus orderStatus
    ) {
        Date date = new Date();

        return Order.builder()
                .code(orderCode)
                .clientCode(clientCode)
                .productCode(productCode)
                .amount(this.faker.number().positive())
                .status(orderStatus)
                .createdAt(date)
                .updatedAt(date)
                .build();
    }

    public Order generateOrder(ClientCode clientCode, ProductCode productCode, OrderStatus orderStatus) {
        return this.generateOrder(
                this.generateOrderCode(),
                clientCode,
                productCode,
                orderStatus
        );
    }

    public Order generateOrder(ClientCode clientCode, ProductCode productCode) {
        return this.generateOrder(clientCode, productCode, OrderStatus.PENDING);
    }

    public List<Order> generateOrderList(ClientCode clientCode, List<ProductCode> productCodes) {
        return productCodes.stream().map(productCode -> this.generateOrder(clientCode, productCode))
                .toList();
    }

    public Order generateOrder(OrderStatus orderStatus) {
        return this.generateOrder(
                this.clientTestDataGenerator.generateClientCode(),
                this.productTestDataGenerator.generateProductCode(),
                orderStatus
        );
    }

    public Order generateOrder() {
        return this.generateOrder(
                this.clientTestDataGenerator.generateClientCode(),
                this.productTestDataGenerator.generateProductCode(),
                OrderStatus.SHIPPED
        );
    }

    public OrderCode generateOrderCode() {
        return OrderCode.create();
    }
}
