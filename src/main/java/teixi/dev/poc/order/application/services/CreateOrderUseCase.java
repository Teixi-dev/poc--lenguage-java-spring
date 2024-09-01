package teixi.dev.poc.order.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.CreateOrderCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderCode;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

@Service
@Transactional
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderResponseMapper mapper;

    public CreateOrderUseCase(
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository,
            OrderResponseMapper mapper
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public OrderResponse execute(CreateOrderCommand command) {
        ClientCode clientCode = ClientCode.builder()
                .value(command.getClientCode())
                .build();

        ProductCode productCode = ProductCode.builder()
                .value(command.getProductCode())
                .build();

        Client client = this.clientRepository.find(clientCode);
        Product product = this.productRepository.find(productCode);

        Product productWithdrawStock = product.withdrawStock(command.getAmount());

        this.productRepository.save(productWithdrawStock);

        Order order = Order.create(
                clientCode,
                productCode,
                command.getAmount()
        );

        this.orderRepository.save(order);

        return this.mapper.map(order, client, productWithdrawStock);
    }
}
