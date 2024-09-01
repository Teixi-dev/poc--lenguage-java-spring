package teixi.dev.poc.product.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.shared.infrastructure.services.UniqueUUIDGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class ProductCode {
    private UUID value;

    public static ProductCode create() {
        return ProductCode.builder()
                .value(UniqueUUIDGenerator.generate())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCode that)) return false;
        return Objects.equals(this.value, that.value);
    }
}
