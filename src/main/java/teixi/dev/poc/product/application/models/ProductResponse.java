package teixi.dev.poc.product.application.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class ProductResponse {
    private String code;
    private String name;
    private String detail;
    private int stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductResponse that)) return false;
        return stock == that.stock && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(detail, that.detail);
    }
}
