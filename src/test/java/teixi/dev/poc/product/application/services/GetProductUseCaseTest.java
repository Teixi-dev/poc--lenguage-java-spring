package teixi.dev.poc.product.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teixi.dev.poc.product.application.mappers.ProductResponseMapper;
import teixi.dev.poc.product.application.models.GetProductCommand;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.product.domain.models.ProductTestDataGenerator;
import teixi.dev.poc.product.domain.repositories.ProductRepository;


public class GetProductUseCaseTest {
    private final ProductRepository repository = Mockito.mock(ProductRepository.class);
    private final ProductResponseMapper mapper = new ProductResponseMapper();
    private final ProductTestDataGenerator productTestDataGenerator = new ProductTestDataGenerator();
    private final GetProductUseCase useCase = new GetProductUseCase(repository, mapper);

    @Test
    public void whenExecuteAndExistProductThenReturnProductResponse() {
        Product product = this.productTestDataGenerator.generateProduct();

        GetProductCommand command = GetProductCommand.builder()
                .productCode(product.getCode().getValue())
                .build();

        Mockito.when(this.repository.find(product.getCode()))
                .thenReturn(product);

        ProductResponse result = this.useCase.execute(command);

        Assertions.assertEquals(product.getCode().getValue().toString(), result.getCode());
        Assertions.assertEquals(product.getName(), result.getName());
        Assertions.assertEquals(product.getDetail(), result.getDetail());
        Assertions.assertEquals(product.getStock(), result.getStock());
    }

    @Test
    public void whenExecuteAndNotExistProductThenThrowProductNotFoundException() {
        ProductCode code = this.productTestDataGenerator.generateProductCode();

        GetProductCommand command = GetProductCommand.builder()
                .productCode(code.getValue())
                .build();

        Mockito.when(this.repository.find(code))
                .thenThrow(new ProductNotFoundException(code));

        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }
}
