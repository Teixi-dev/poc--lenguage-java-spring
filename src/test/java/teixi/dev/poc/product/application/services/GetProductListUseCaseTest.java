package teixi.dev.poc.product.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teixi.dev.poc.product.application.mappers.ProductResponseMapper;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;
import teixi.dev.poc.product.domain.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class GetProductListUseCaseTest {
    private final ProductRepository repository = Mockito.mock(ProductRepository.class);
    private final ProductResponseMapper mapper = new ProductResponseMapper();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();
    private final GetProductListUseCase useCase = new GetProductListUseCase(repository, mapper);

    private static final int EXPECTED_EMPTY_LIST_SIZE = 0;

    @Test
    public void whenExecuteAndHaveProductsThenReturnProductResponseList() {
        List<Product> products = this.productTestDataGenerator.generateProductList();

        Mockito.when(this.repository.searchAll())
                .thenReturn(products);

        List<ProductResponse> result = this.useCase.execute();

        Assertions.assertEquals(products.size(), result.size());

        IntStream.range(0, products.size()).forEach(i -> {
            Product product = products.get(i);
            ProductResponse productResponse = result.get(i);

            Assertions.assertEquals(product.getCode().getValue().toString(), productResponse.getCode());
            Assertions.assertEquals(product.getName(), productResponse.getName());
            Assertions.assertEquals(product.getDetail(), productResponse.getDetail());
            Assertions.assertEquals(product.getStock(), productResponse.getStock());
        });
    }

    @Test
    public void whenExecuteButNotHaveProductsThenReturnEmptyList() {
        Mockito.when(this.repository.searchAll())
                .thenReturn(Collections.emptyList());

        List<ProductResponse> result = this.useCase.execute();

        Assertions.assertEquals(EXPECTED_EMPTY_LIST_SIZE, result.size());
    }
}
