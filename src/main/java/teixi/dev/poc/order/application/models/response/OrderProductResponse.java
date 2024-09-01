package teixi.dev.poc.order.application.models.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class OrderProductResponse {
    private UUID code;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProductResponse that)) return false;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name);
    }
}
