package teixi.dev.poc.product.domain.repositories;

import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.shared.domain.models.ProductCode;

import java.util.List;

public interface ProductRepository {
    public List<Product> searchAll();

    public Product find(ProductCode productCode);

    public List<Product> find(List<ProductCode> productsCode);

    public void save(Product product);
}
