package teixi.dev.poc.order.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.GetOrderByCodeCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.exceptions.OrderNotFoundException;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderCode;
import teixi.dev.poc.order.domain.models.OrderTestDataGenerator;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

public class GetOrderByCodeUseCaseTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final OrderResponseMapper mapper = new OrderResponseMapper();
    private final OrderTestDataGenerator orderTestDataGenerator = new OrderTestDataGenerator();
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();
    private final GetOrderByCodeUseCase useCase = new GetOrderByCodeUseCase(
            orderRepository,
            clientRepository,
            productRepository,
            mapper
    );

    @Test
    public void whenExecuteAndOrderExistThenReturnOrderResponse() {
        Client client = this.clientTestDataGenerator.generateClient();
        Product product = this.productTestDataGenerator.generateProduct();
        Order order = this.orderTestDataGenerator.generateOrder(client.getCode(), product.getCode());

        Mockito.when(this.orderRepository.find(order.getCode()))
                .thenReturn(order);
        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.productRepository.find(product.getCode()))
                .thenReturn(product);

        GetOrderByCodeCommand command = GetOrderByCodeCommand.builder()
                .orderCode(order.getCode().getValue())
                .build();

        OrderResponse result = this.useCase.execute(command);

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

    @Test
    public void whenExecuteButOrderNotExistThenThrowOrderNotFoundException() {
        OrderCode orderCode = this.orderTestDataGenerator.generateOrderCode();

        Mockito.when(this.orderRepository.find(orderCode))
                .thenThrow(OrderNotFoundException.class);

        GetOrderByCodeCommand command = GetOrderByCodeCommand.builder()
                .orderCode(orderCode.getValue())
                .build();

        Assertions.assertThrows(
                OrderNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }

    @Test
    public void whenExecuteButClientNotExistThenThrowClientNotFoundException() {
        Order order = this.orderTestDataGenerator.generateOrder();

        Mockito.when(this.orderRepository.find(order.getCode()))
                .thenReturn(order);

        Mockito.when(this.clientRepository.find(order.getClientCode()))
                .thenThrow(ClientNotFoundException.class);

        GetOrderByCodeCommand command = GetOrderByCodeCommand.builder()
                .orderCode(order.getCode().getValue())
                .build();

        Assertions.assertThrows(
                ClientNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }

    @Test
    public void whenExecuteButProductNotExistThenThrowProductNotFoundException() {
        Client client = this.clientTestDataGenerator.generateClient();
        Order order = this.orderTestDataGenerator.generateOrder(
                client.getCode(),
                this.productTestDataGenerator.generateProductCode()
        );

        Mockito.when(this.orderRepository.find(order.getCode()))
                .thenReturn(order);

        Mockito.when(this.clientRepository.find(order.getClientCode()))
                .thenReturn(client);

        Mockito.when(this.productRepository.find(order.getProductCode()))
                .thenThrow(ProductNotFoundException.class);

        GetOrderByCodeCommand command = GetOrderByCodeCommand.builder()
                .orderCode(order.getCode().getValue())
                .build();

        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }
}
