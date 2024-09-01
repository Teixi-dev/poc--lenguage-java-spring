package teixi.dev.poc.order.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.shared.infrastructure.services.UniqueUUIDGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class OrderCode {
    private UUID value;

    public static OrderCode create() {
        return OrderCode.builder()
                .value(UniqueUUIDGenerator.generate())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCode orderCode)) return false;
        return Objects.equals(value, orderCode.value);
    }
}
