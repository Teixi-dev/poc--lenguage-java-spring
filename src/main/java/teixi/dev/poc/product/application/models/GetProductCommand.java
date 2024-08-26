package teixi.dev.poc.product.application.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductCommand {
    private String productCode;
}
