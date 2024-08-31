package teixi.dev.poc.product.application.mappers;

import org.springframework.stereotype.Service;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.application.models.ProductResponse;

@Service
public class ProductResponseMapper {

    public ProductResponse map(Product product) {
        return ProductResponse.builder()
                .code(product.getCode().getValue().toString())
                .name(product.getName())
                .detail(product.getDetail())
                .stock(product.getStock())
                .build();
    }
}
