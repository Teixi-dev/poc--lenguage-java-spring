package teixi.dev.poc.order.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class OrderCode {
    private UUID value;

    public static OrderCode create() {
        return OrderCode.builder()
                .value(UUID.randomUUID())
                .build();
    }
}
