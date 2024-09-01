package teixi.dev.poc.order.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.GetOrdersByClientCodeCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderTestDataGenerator;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class GetOrdersByClientCodeUseCaseTest {
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final OrderResponseMapper mapper = new OrderResponseMapper();
    private final OrderTestDataGenerator orderTestDataGenerator = new OrderTestDataGenerator();
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();
    private final GetOrdersByClientCodeUseCase useCase = new GetOrdersByClientCodeUseCase(
            orderRepository,
            clientRepository,
            productRepository,
            mapper
    );

    private static final int EXPECTED_ZERO_SIZE = 0;

    @Test
    public void whenExecuteAndClientExistAndaHaveOrdersThenReturnOrderResponse() {
        Client client = this.clientTestDataGenerator.generateClient();
        List<Product> products = this.productTestDataGenerator.generateProductList();
        List<Order> orders = this.orderTestDataGenerator.generateOrderList(
                client.getCode(),
                products.stream().map(Product::getCode).toList()
        );

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.orderRepository.searchByClientCode(client.getCode()))
                .thenReturn(orders);
        Mockito.when(this.productRepository.find(orders.stream().map(Order::getProductCode).distinct().toList()))
                .thenReturn(products);

        GetOrdersByClientCodeCommand command = GetOrdersByClientCodeCommand.builder()
                .clientCode(client.getCode().getValue())
                .build();

        List<OrderResponse> result = this.useCase.execute(command);

        Assertions.assertEquals(orders.size(), result.size());

        IntStream.range(0, orders.size()).forEach(i -> {
            Order order = orders.get(i);
            OrderResponse orderResponse = result.get(i);
            Product product = products.stream().filter(productFilter ->
                    productFilter.getCode().equals(order.getProductCode())
            ).findFirst().orElseThrow();

            Assertions.assertEquals(order.getCode().getValue(), orderResponse.getCode());
            Assertions.assertEquals(order.getAmount(), orderResponse.getAmount());
            Assertions.assertEquals(order.getStatus().getValue(), orderResponse.getStatus());
            Assertions.assertEquals(order.getCreatedAt(), orderResponse.getCreatedAt());
            Assertions.assertEquals(order.getUpdatedAt(), orderResponse.getUpdatedAt());
            Assertions.assertEquals(client.getCode().getValue(), orderResponse.getClient().getCode());
            Assertions.assertEquals(client.getName(), orderResponse.getClient().getName());
            Assertions.assertEquals(product.getCode().getValue(), orderResponse.getProduct().getCode());
            Assertions.assertEquals(product.getName(), orderResponse.getProduct().getName());
        });
    }

    @Test
    public void whenExecuteAndClientExistButNotHaveOrdersThenReturnEmptyList() {
        Client client = this.clientTestDataGenerator.generateClient();

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.orderRepository.searchByClientCode(client.getCode()))
                .thenReturn(Collections.emptyList());

        GetOrdersByClientCodeCommand command = GetOrdersByClientCodeCommand.builder()
                .clientCode(client.getCode().getValue())
                .build();

        List<OrderResponse> result = this.useCase.execute(command);

        Assertions.assertEquals(EXPECTED_ZERO_SIZE, result.size());
    }

    @Test
    public void whenExecuteAndClientNotExistThenThrowClientNotFound() {
        Client client = this.clientTestDataGenerator.generateClient();

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenThrow(ClientNotFoundException.class);

        GetOrdersByClientCodeCommand command = GetOrdersByClientCodeCommand.builder()
                .clientCode(client.getCode().getValue())
                .build();

        Assertions.assertThrows(
                ClientNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }

    @Test
    public void whenExecuteButSomeProductNotExistThenThrowProductNotFoundException() {
        Client client = this.clientTestDataGenerator.generateClient();
        List<Product> products = this.productTestDataGenerator.generateProductList();
        List<Order> orders = this.orderTestDataGenerator.generateOrderList(
                client.getCode(),
                products.stream().map(Product::getCode).toList()
        );

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.orderRepository.searchByClientCode(client.getCode()))
                .thenReturn(orders);

        Mockito.when(this.productRepository.find(orders.stream().map(Order::getProductCode).distinct().toList()))
                .thenThrow(ProductNotFoundException.class);

        GetOrdersByClientCodeCommand command = GetOrdersByClientCodeCommand.builder()
                .clientCode(client.getCode().getValue())
                .build();

        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }
}
