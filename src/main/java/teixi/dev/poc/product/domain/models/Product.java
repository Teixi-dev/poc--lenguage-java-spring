package teixi.dev.poc.product.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.product.domain.exception.InsufficientProductStockException;
import teixi.dev.poc.shared.domain.models.ProductCode;

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
}
