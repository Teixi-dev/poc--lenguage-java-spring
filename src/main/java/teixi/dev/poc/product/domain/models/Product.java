package teixi.dev.poc.product.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.shared.domain.models.ProductCode;

@Getter
@Builder
public class Product {
    private ProductCode code;
    private String name;
    private String detail;
    private float price;
    private int stock;
}
