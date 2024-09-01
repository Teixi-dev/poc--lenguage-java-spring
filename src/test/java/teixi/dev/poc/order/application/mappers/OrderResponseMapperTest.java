package teixi.dev.poc.order.application.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderTestDataGenerator;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;

public class OrderResponseMapperTest {
    private final OrderResponseMapper mapper = new OrderResponseMapper();
    private final OrderTestDataGenerator orderTestDataGenerator = new OrderTestDataGenerator();
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();

    @Test
    public void whenMapCorrectOrderThenReturnOrderResponse() {
        Client client = this.clientTestDataGenerator.generateClient();
        Product product = this.productTestDataGenerator.generateProduct();
        Order order = this.orderTestDataGenerator.generateOrder(client.getCode(), product.getCode());

        OrderResponse result = this.mapper.map(order, client, product);

        Assertions.assertEquals(order.getCode().getValue(), result.getCode());
        Assertions.assertEquals(order.getAmount(), result.getAmount());
        Assertions.assertEquals(order.getStatus().getValue(), result.getStatus());
        Assertions.assertEquals(order.getCreatedAt(), result.getCreatedAt());
        Assertions.assertEquals(order.getUpdatedAt(), result.getUpdatedAt());
        Assertions.assertEquals(client.getCode().getValue(), result.getClient().getCode());
        Assertions.assertEquals(client.getName(), result.getClient().getName());
        Assertions.assertEquals(product.getCode().getValue(), result.getProduct().getCode());
        Assertions.assertEquals(product.getName(), result.getProduct().getName());
    }
}
