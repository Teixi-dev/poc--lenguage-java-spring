package teixi.dev.poc.product.infrastructure.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.product.application.models.ProductResponse;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

public class GetProductControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private static final String URL_TEMPLATE = "/products/{productCode}";
    private static final String BASE_URL = "http://localhost";
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
    public void whenCallGetProductAndExistThenReturnProductResponse() {
        ProductResponse result = RestAssured.given()
                .when()
                .get(
                        URL_TEMPLATE,
                        MONITOR_PRODUCT.getCode()
                )
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ProductResponse.class);

        Assertions.assertEquals(MONITOR_PRODUCT, result);
    }

    @Test
    public void whenCallGetProductAndNotExistThenReturnNotFoundStatus() {
        RestAssured.given()
                .when()
                .get(
                        URL_TEMPLATE,
                        ProductCode.create().getValue().toString()
                )
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}