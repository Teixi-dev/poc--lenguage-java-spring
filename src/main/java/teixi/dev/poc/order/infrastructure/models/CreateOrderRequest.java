package teixi.dev.poc.order.infrastructure.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderRequest {
    private String clientCode;
    private String productCode;
    private int amount;
}
