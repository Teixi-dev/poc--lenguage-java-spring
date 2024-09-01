package teixi.dev.poc.product.infrastructure.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.io.IOException;
import java.util.List;

public class GetProductListControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private static final int EXPECTED_SEARCH_ALL_EMPTY_SIZE = 0;
    private static final String URL_TEMPLATE = "/products";
    private static final String BASE_URL = "http://localhost";
    public static final String DELETE_FROM_PRODUCTS = "DELETE FROM products";
    private static final String EMPTY_STRING = "";
    private static final ProductResponse LAPTOP_PRODUCT = ProductResponse.builder()
            .code("a3e5b2e8-7e6b-11ec-90d6-0242ac120003")
            .name("Laptop")
            .detail("Laptop con 16GB RAM y 512GB SSD")
            .stock(15)
            .build();
    private static final ProductResponse SMARTPHONE_PRODUCT = ProductResponse.builder()
            .code("a3e5b8e6-7e6b-11ec-90d6-0242ac120003")
            .name("Smartphone")
            .detail("Smartphone con pantalla AMOLED")
            .stock(30)
            .build();
    private static final ProductResponse MONITOR_PRODUCT = ProductResponse.builder()
            .code("a3e5bc1a-7e6b-11ec-90d6-0242ac120003")
            .name("Monitor")
            .detail("Monitor 27 pulgadas 4K")
            .stock(20)
            .build();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void whenCallGetProductListAndHaveValuesThenReturnProductResponseList() {
        List<ProductResponse> expectedProducts = List.of(SMARTPHONE_PRODUCT, LAPTOP_PRODUCT, MONITOR_PRODUCT);

        List<ProductResponse> result = RestAssured.given()
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(EMPTY_STRING, ProductResponse.class);

        Assertions.assertEquals(expectedProducts.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedProducts));
    }

    @Test
    public void whenCallGetProductListAndNotHaveThenReturnEmptyList() throws IOException {
        jdbcTemplate.execute(DELETE_FROM_PRODUCTS);

        List<ProductResponse> result = RestAssured.given()
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(EMPTY_STRING, ProductResponse.class);

        Assertions.assertEquals(EXPECTED_SEARCH_ALL_EMPTY_SIZE, result.size());

        this.restartDatabase();
    }
}
