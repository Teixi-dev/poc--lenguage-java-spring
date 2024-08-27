package teixi.dev.poc.product.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import teixi.dev.poc.product.application.services.GetProductListUseCase;
import teixi.dev.poc.product.infrastructure.mappers.ProductViewMapper;
import teixi.dev.poc.product.infrastructure.models.ProductView;

import java.util.List;

@Controller
public class GetProductListController {

    private final GetProductListUseCase useCase;
    private final ProductViewMapper mapper;

    public GetProductListController(GetProductListUseCase useCase, ProductViewMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductView>> getProductList() {
        return ResponseEntity.ok(this.useCase.execute().stream().map(this.mapper::map).toList());
    }
}
