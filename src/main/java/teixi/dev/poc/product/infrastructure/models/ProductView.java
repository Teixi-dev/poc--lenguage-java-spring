package teixi.dev.poc.product.infrastructure.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductView {
    private String code;
    private String name;
    private String detail;
    private float price;
    private int stock;
}
