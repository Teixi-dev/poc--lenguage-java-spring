package teixi.dev.poc.product.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.product.application.mappers.ProductResponseMapper;
import teixi.dev.poc.product.application.models.GetProductCommand;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

import java.util.UUID;

@Service
public class GetProductUseCase {
    private final ProductRepository repository;
    private final ProductResponseMapper mapper;

    public GetProductUseCase(ProductRepository repository, ProductResponseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponse execute(GetProductCommand command) {
        ProductCode productCode = ProductCode.builder()
                .value(command.getProductCode())
                .build();

        return this.mapper.map(this.repository.find(productCode));
    }
}
