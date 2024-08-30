package teixi.dev.poc.order.application.models.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderProductResponse {
    private UUID code;
    private String name;
}
