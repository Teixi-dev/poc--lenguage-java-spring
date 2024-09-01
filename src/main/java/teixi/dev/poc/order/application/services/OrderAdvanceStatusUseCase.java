package teixi.dev.poc.order.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.OrderAdvanceStatusCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.models.OrderCode;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

@Service
public class OrderAdvanceStatusUseCase {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderResponseMapper mapper;

    public OrderAdvanceStatusUseCase(
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

    public OrderResponse execute(OrderAdvanceStatusCommand command) {
        OrderCode orderCode = OrderCode.builder()
                .value(command.getOrderCode())
                .build();

        Order order = this.orderRepository.find(orderCode);
        Order advancedOrder = order.advanceStatus();

        this.orderRepository.save(advancedOrder);

        Client client = this.clientRepository.find(advancedOrder.getClientCode());
        Product product = this.productRepository.find(advancedOrder.getProductCode());

        return this.mapper.map(advancedOrder, client, product);
    }
}
