package teixi.dev.poc.order.application.models.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderResponse that)) return false;
        return amount == that.amount && Objects.equals(code, that.code) && Objects.equals(status, that.status) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(createdAt, that.createdAt) && Objects.equals(client, that.client) && Objects.equals(product, that.product);
    }
}
