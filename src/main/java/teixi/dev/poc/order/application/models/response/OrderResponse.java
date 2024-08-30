package teixi.dev.poc.order.application.models.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class OrderResponse {
    private UUID code;
    private int amount;
    private String status;
    private Date updatedAt;
    private Date createdAt;
    private OrderClientResponse client;
    private OrderProductResponse product;
}
