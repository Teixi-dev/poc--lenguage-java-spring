package teixi.dev.poc.order.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.GetOrderByCodeCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.repositories.ProductRepository;
import teixi.dev.poc.shared.domain.models.OrderCode;

@Service
public class GetOrderByCodeUseCase {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderResponseMapper mapper;

    public GetOrderByCodeUseCase(
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

    public OrderResponse execute(GetOrderByCodeCommand command) {
        OrderCode orderCode = OrderCode.builder()
                .value(command.getOrderCode())
                .build();

        Order order = this.orderRepository.find(orderCode);
        Client client = this.clientRepository.find(order.getClientCode());
        Product product = this.productRepository.find(order.getProductCode());

        return this.mapper.map(order, client, product);
    }
}
