package teixi.dev.poc.client.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.shared.infrastructure.services.UniqueUUIDGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class ClientCode {
    private UUID value;

    public static ClientCode create() {
        return ClientCode.builder()
                .value(UniqueUUIDGenerator.generate())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientCode that)) return false;
        return Objects.equals(value, that.value);
    }
}
