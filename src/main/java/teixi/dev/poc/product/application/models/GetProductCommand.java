package teixi.dev.poc.product.application.models;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetProductCommand {
    private UUID productCode;
}
