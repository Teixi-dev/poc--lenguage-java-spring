package teixi.dev.poc.product.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.application.services.GetProductListUseCase;

import java.util.List;

@Controller
public class GetProductListController {
    private final GetProductListUseCase useCase;

    public GetProductListController(GetProductListUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductResponse>> getProductList() {
        return ResponseEntity.ok(this.useCase.execute());
    }
}
