package teixi.dev.poc.product.application.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private String code;
    private String name;
    private String detail;
    private int stock;
}
