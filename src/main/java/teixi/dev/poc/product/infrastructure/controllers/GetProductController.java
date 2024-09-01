package teixi.dev.poc.product.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.product.application.models.GetProductCommand;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.application.services.GetProductUseCase;

import java.util.UUID;

@Controller
public class GetProductController {
    private final GetProductUseCase useCase;

    private GetProductController(GetProductUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/products/{productCode}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String productCode) {
        GetProductCommand command = GetProductCommand.builder()
                .productCode(UUID.fromString(productCode))
                .build();

        return ResponseEntity.ok(this.useCase.execute(command));
    }
}
