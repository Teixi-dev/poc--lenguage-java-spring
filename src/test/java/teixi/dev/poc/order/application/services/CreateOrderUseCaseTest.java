package teixi.dev.poc.order.application.services;

import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.CreateOrderCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.exceptions.InvalidOrderAmountException;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderStatus;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.exception.InsufficientProductStockException;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

public class CreateOrderUseCaseTest {
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();
    private final Faker faker = new Faker();
    private final OrderResponseMapper mapper = new OrderResponseMapper();
    private final CreateOrderUseCase useCase = new CreateOrderUseCase(
            orderRepository,
            clientRepository,
            productRepository,
            mapper
    );

    private static final int MIN_PRODUCT_AMOUNT = 1;

    @Test
    public void whenCreateCorrectOrderThenCreateAndReturnOrderResponse() {
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        Client client = this.clientTestDataGenerator.generateClient();
        Product product = this.productTestDataGenerator.generateProduct();

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.productRepository.find(product.getCode()))
                .thenReturn(product);

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientCode(client.getCode().getValue())
                .productCode(product.getCode().getValue())
                .amount(this.faker.number().numberBetween(MIN_PRODUCT_AMOUNT, product.getStock()))
                .build();

        OrderResponse result = this.useCase.execute(command);

        Mockito.verify(this.productRepository)
                .save(productCaptor.capture());
        Mockito.verify(this.orderRepository)
                .save(orderCaptor.capture());

        Product productCaptured = productCaptor.getValue();

        int expectedStock = product.getStock() - command.getAmount();

        Assertions.assertEquals(product.getCode(), productCaptured.getCode());
        Assertions.assertEquals(product.getName(), productCaptured.getName());
        Assertions.assertEquals(expectedStock, productCaptured.getStock());

        Order orderCaptured = orderCaptor.getValue();

        Assertions.assertEquals(client.getCode(), orderCaptured.getClientCode());
        Assertions.assertEquals(product.getCode(), orderCaptured.getProductCode());
        Assertions.assertEquals(command.getAmount(), orderCaptured.getAmount());
        Assertions.assertEquals(OrderStatus.PENDING, orderCaptured.getStatus());

        Assertions.assertEquals(command.getAmount(), result.getAmount());
        Assertions.assertEquals(OrderStatus.PENDING.getValue(), result.getStatus());
        Assertions.assertEquals(client.getCode().getValue(), result.getClient().getCode());
        Assertions.assertEquals(client.getName(), result.getClient().getName());
        Assertions.assertEquals(product.getCode().getValue(), result.getProduct().getCode());
        Assertions.assertEquals(product.getName(), result.getProduct().getName());
    }

    @Test
    public void whenCreateOrderButClientNotExistThenThrowClientNotFoundException() {
        ClientCode clientCode = this.clientTestDataGenerator.generateClientCode();
        Mockito.when(this.clientRepository.find(clientCode))
                .thenThrow(ClientNotFoundException.class);

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientCode(clientCode.getValue())
                .productCode(this.productTestDataGenerator.generateProductCode().getValue())
                .amount(this.faker.number().positive())
                .build();

        Assertions.assertThrows(
                ClientNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }

    @Test
    public void whenCreateOrderButProductNotExistThenThrowProductNotFoundException() {
        Client client = this.clientTestDataGenerator.generateClient();
        ProductCode productCode = this.productTestDataGenerator.generateProductCode();

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.productRepository.find(productCode))
                .thenThrow(ProductNotFoundException.class);

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientCode(client.getCode().getValue())
                .productCode(productCode.getValue())
                .amount(this.faker.number().positive())
                .build();

        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }

    @Test
    public void whenCreateCorrectOrderButNotHaveStockThenReturnInsufficientProductStockException() {
        Client client = this.clientTestDataGenerator.generateClient();
        Product product = this.productTestDataGenerator.generateProduct();

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.productRepository.find(product.getCode()))
                .thenReturn(product);

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientCode(client.getCode().getValue())
                .productCode(product.getCode().getValue())
                .amount(this.faker.number().numberBetween(product.getStock() + 1, Integer.MAX_VALUE))
                .build();

        Assertions.assertThrows(
                InsufficientProductStockException.class,
                () -> this.useCase.execute(command)
        );
    }

    @Test
    public void whenCreateOrderThenWithInvalidAmountThenThrowInvalidOrderAmountException() {
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        Client client = this.clientTestDataGenerator.generateClient();
        Product product = this.productTestDataGenerator.generateProduct();

        Mockito.when(this.clientRepository.find(client.getCode()))
                .thenReturn(client);
        Mockito.when(this.productRepository.find(product.getCode()))
                .thenReturn(product);

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientCode(client.getCode().getValue())
                .productCode(product.getCode().getValue())
                .amount(this.faker.number().negative())
                .build();

        Assertions.assertThrows(
                InvalidOrderAmountException.class,
                () -> this.useCase.execute(command)
        );
    }
}
