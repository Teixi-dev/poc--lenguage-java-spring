package teixi.dev.poc.product.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.product.application.models.GetProductCommand;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.repositories.ProductRepository;
import teixi.dev.poc.shared.domain.models.ProductCode;

import java.util.UUID;

@Service
public class GetProductUseCase {

    private final ProductRepository repository;

    public GetProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public Product execute(GetProductCommand command) {
        ProductCode productCode = ProductCode.builder()
                .value(UUID.fromString(command.getProductCode()))
                .build();

        return this.repository.find(productCode);
    }
}
