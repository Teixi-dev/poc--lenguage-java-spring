package teixi.dev.poc.product.application.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import teixi.dev.poc.product.application.mappers.ProductResponseMapper;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;

public class ProductResponseMapperTest {
    private final ProductResponseMapper mapper = new ProductResponseMapper();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();

    @Test
    public void whenMapCorrectProductThenReturnProductResponse() {
        Product product = this.productTestDataGenerator.generateProduct();

        ProductResponse result = this.mapper.map(product);

        Assertions.assertEquals(product.getCode().getValue().toString(), result.getCode());
        Assertions.assertEquals(product.getName(), result.getName());
        Assertions.assertEquals(product.getDetail(), result.getDetail());
        Assertions.assertEquals(product.getStock(), result.getStock());
    }
}
