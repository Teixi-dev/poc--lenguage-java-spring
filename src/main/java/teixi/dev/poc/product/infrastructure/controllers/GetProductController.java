package teixi.dev.poc.product.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.product.application.models.GetProductCommand;
import teixi.dev.poc.product.application.services.GetProductUseCase;
import teixi.dev.poc.product.infrastructure.mappers.ProductViewMapper;
import teixi.dev.poc.product.infrastructure.models.ProductView;

@Controller
public class GetProductController {
    private final GetProductUseCase useCase;
    private final ProductViewMapper mapper;

    private GetProductController(GetProductUseCase useCase, ProductViewMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping(value = "/products/{productCode}")
    public ResponseEntity<ProductView> getProduct(@PathVariable String productCode) {
        GetProductCommand command = GetProductCommand.builder()
                .productCode(productCode)
                .build();

        return ResponseEntity.ok(this.mapper.map(this.useCase.execute(command)));
    }
}
