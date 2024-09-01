package teixi.dev.poc.product.domain.models;


import net.datafaker.Faker;

import java.util.List;
import java.util.stream.IntStream;


public class ProductTestDataGenerator {
    private final Faker faker = new Faker();

    public static final int ZERO_VALUE = 0;
    public static final int MIN_NUMBER_OF_PRODUCTS = 2;
    public static final int MAX_NUMBER_OF_PRODUCTS = 10;

    public Product generateProduct() {
        return Product.builder()
                .code(this.generateProductCode())
                .name(this.faker.food().fruit())
                .detail(this.faker.lorem().sentence())
                .stock(this.faker.number().positive())
                .build();
    }

    public ProductCode generateProductCode() {
        return ProductCode.create();
    }

    public List<Product> generateProductList() {
        int numberOfProducts = this.faker.number().numberBetween(MIN_NUMBER_OF_PRODUCTS, MAX_NUMBER_OF_PRODUCTS);
        return IntStream.range(ZERO_VALUE, numberOfProducts)
                .mapToObj(_ -> generateProduct())
                .toList();
    }
}
