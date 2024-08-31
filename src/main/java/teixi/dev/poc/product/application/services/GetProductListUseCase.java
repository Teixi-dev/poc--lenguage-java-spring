package teixi.dev.poc.product.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.product.application.mappers.ProductResponseMapper;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

import java.util.List;

@Service
public class GetProductListUseCase {
    private final ProductRepository repository;
    private final ProductResponseMapper mapper;

    public GetProductListUseCase(ProductRepository repository, ProductResponseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProductResponse> execute() {
        return repository.searchAll().stream().map(mapper::map).toList();
    }
}
