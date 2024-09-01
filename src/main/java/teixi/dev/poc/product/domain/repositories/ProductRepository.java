package teixi.dev.poc.product.domain.repositories;

import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;

import java.util.List;

public interface ProductRepository {
    public List<Product> searchAll();

    public Product find(ProductCode productCode) throws ProductNotFoundException;

    public List<Product> find(List<ProductCode> productsCode) throws ProductNotFoundException;

    public void save(Product product);
}
