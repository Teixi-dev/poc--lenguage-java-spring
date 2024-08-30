package teixi.dev.poc.order.application.mappers;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.order.application.models.response.OrderClientResponse;
import teixi.dev.poc.order.application.models.response.OrderProductResponse;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.product.domain.models.Product;

@Service
public class OrderResponseMapper {

    public OrderResponse map(Order order, Client client, Product product) {
        return OrderResponse.builder()
                .code(order.getCode().getValue())
                .amount(order.getAmount())
                .status(order.getStatus().getValue())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .client(OrderClientResponse.builder()
                        .code(client.getCode().getValue())
                        .name(client.getName())
                        .build()
                )
                .product(OrderProductResponse.builder()
                        .code(product.getCode().getValue())
                        .name(product.getName())
                        .build()
                )
                .build();
    }
}
