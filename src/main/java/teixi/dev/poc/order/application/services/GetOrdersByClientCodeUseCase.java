package teixi.dev.poc.order.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.order.application.mappers.OrderResponseMapper;
import teixi.dev.poc.order.application.models.GetOrdersByClientCodeCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.repositories.ProductRepository;
import teixi.dev.poc.shared.domain.models.ClientCode;

import java.util.Collections;
import java.util.List;

@Service
public class GetOrdersByClientCodeUseCase {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderResponseMapper mapper;

    public GetOrdersByClientCodeUseCase(
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

    public List<OrderResponse> execute(GetOrdersByClientCodeCommand command) {
        ClientCode clientCode = ClientCode.builder()
                .value(command.getClientCode())
                .build();

        Client client = clientRepository.find(clientCode);

        List<Order> orders = orderRepository.searchByClientCode(clientCode);

        if (orders.isEmpty())
            return Collections.emptyList();

        List<Product> products = productRepository.find(orders.stream().map(Order::getProductCode).distinct().toList());

        return orders.stream().map(order ->
                this.mapper.map(
                        order,
                        client,
                        products.stream().filter(product ->
                                product.getCode().equals(order.getProductCode())
                        ).findFirst().orElseThrow()
                )
        ).toList();
    }
}
