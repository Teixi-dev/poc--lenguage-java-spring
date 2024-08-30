package teixi.dev.poc.product.infrastructure.mappers;

import org.springframework.stereotype.Service;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.infrastructure.models.ProductView;

@Service
public class ProductViewMapper {

    public ProductView map(Product product) {
        return ProductView.builder()
                .code(product.getCode().getValue().toString())
                .name(product.getName())
                .detail(product.getDetail())
                .stock(product.getStock())
                .build();
    }
}
