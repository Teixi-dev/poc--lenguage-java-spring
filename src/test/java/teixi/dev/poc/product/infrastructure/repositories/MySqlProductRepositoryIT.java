package teixi.dev.poc.product.infrastructure.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MySqlProductRepositoryIT extends IntegrationTestWithApplicationContainers {
    public static final String DELETE_FROM_PRODUCTS = "DELETE FROM products";
    @Autowired
    private MySqlProductRepository repository;

    private static final Product LAPTOP_PRODUCT = Product.builder()
            .code(ProductCode.builder().value(UUID.fromString("a3e5b2e8-7e6b-11ec-90d6-0242ac120003")).build())
            .name("Laptop")
            .detail("Laptop con 16GB RAM y 512GB SSD")
            .stock(15)
            .build();
    private static final Product SMARTPHONE_PRODUCT = Product.builder()
            .code(ProductCode.builder().value(UUID.fromString("a3e5b8e6-7e6b-11ec-90d6-0242ac120003")).build())
            .name("Smartphone")
            .detail("Smartphone con pantalla AMOLED")
            .stock(30)
            .build();
    private static final Product MONITOR_PRODUCT = Product.builder()
            .code(ProductCode.builder().value(UUID.fromString("a3e5bc1a-7e6b-11ec-90d6-0242ac120003")).build())
            .name("Monitor")
            .detail("Monitor 27 pulgadas 4K")
            .stock(20)
            .build();
    private static final int WITHDRAW_STOCK_AMOUNT = 12;
    private static final int EXPECTED_EMPTY_LIST_SIZE = 0;

    @Test
    public void whenFindAndExistThenReturnProduct() {
        Product result = this.repository.find(LAPTOP_PRODUCT.getCode());

        Assertions.assertEquals(LAPTOP_PRODUCT, result);
    }

    @Test
    public void whenFindAndNotExistThenReturnProduct() {
        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> this.repository.find(ProductCode.create())
        );
    }

    @Test
    public void whenFindByListOfCodesAndExistThenReturnListOfProducts() {
        List<ProductCode> productCodesToSearch = List.of(
                LAPTOP_PRODUCT.getCode(),
                MONITOR_PRODUCT.getCode()
        );

        List<Product> result = this.repository.find(productCodesToSearch);

        Assertions.assertEquals(productCodesToSearch.size(), result.size());
        Assertions.assertTrue(result.containsAll(List.of(LAPTOP_PRODUCT, MONITOR_PRODUCT)));
    }

    @Test
    public void whenFindByListOfCodesButSomeOneNotExistThenReturnListOfProducts() {
        List<ProductCode> productCodesToSearch = List.of(
                LAPTOP_PRODUCT.getCode(),
                ProductCode.create()
        );

        Assertions.assertThrows(
                ProductNotFoundException.class,
                () -> this.repository.find(productCodesToSearch)
        );
    }

    @Test
    public void whenSaveProductAndExistThenUpdateExistingProduct() throws IOException {
        Product product = this.repository.find(LAPTOP_PRODUCT.getCode());

        Product productWithStockWithdrawn = product.withdrawStock(WITHDRAW_STOCK_AMOUNT);

        this.repository.save(productWithStockWithdrawn);

        Product productFoundWithStockWithdrawn = this.repository.find(LAPTOP_PRODUCT.getCode());

        Assertions.assertEquals(productWithStockWithdrawn, productFoundWithStockWithdrawn);

        this.restartDatabase();
    }

    @Test
    public void whenSearchAllProductsAndHaveThenReturnListOfProducts() {
        List<Product> expectedProducts = List.of(LAPTOP_PRODUCT, SMARTPHONE_PRODUCT, MONITOR_PRODUCT);

        List<Product> result = this.repository.searchAll();

        Assertions.assertEquals(expectedProducts.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedProducts));
    }

    @Test
    public void whenSearchAllProductsButNotHaveThenReturnEmptyList() throws IOException {
        jdbcTemplate.execute(DELETE_FROM_PRODUCTS);

        List<Product> result = this.repository.searchAll();

        Assertions.assertEquals(EXPECTED_EMPTY_LIST_SIZE, result.size());

        this.restartDatabase();
    }
}
