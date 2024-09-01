package teixi.dev.poc.product.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.product.domain.exception.InsufficientProductStockException;

import java.util.Objects;

@Getter
@Builder
public class Product {
    private ProductCode code;
    private String name;
    private String detail;
    private int stock;

    public Product withdrawStock(int amount) {
        if (stock < amount)
            throw new InsufficientProductStockException(this.getCode());

        return Product.builder()
                .code(this.code)
                .name(this.name)
                .detail(this.getDetail())
                .stock(stock - amount)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return stock == product.stock && Objects.equals(code, product.code) && Objects.equals(name, product.name) && Objects.equals(detail, product.detail);
    }
}
