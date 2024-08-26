package teixi.dev.poc.product.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

import java.util.List;

@Service
public class GetProductListUseCase {

    private final ProductRepository repository;

    public GetProductListUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> execute() {
        return repository.searchAll();
    }
}
