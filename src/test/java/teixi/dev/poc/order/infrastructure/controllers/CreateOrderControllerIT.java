package teixi.dev.poc.order.infrastructure.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.order.application.models.response.OrderClientResponse;
import teixi.dev.poc.order.application.models.response.OrderProductResponse;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.infrastructure.models.CreateOrderRequest;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.io.IOException;
import java.util.UUID;

public class CreateOrderControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private final Faker faker = new Faker();

    private static final String URL_TEMPLATE = "/orders";
    private static final String BASE_URL = "http://localhost";
    private static final OrderResponse SOME_ORDER_RESPONSE = OrderResponse.builder()
            .amount(2)
            .status("Pending")
            .client(OrderClientResponse.builder()
                    .code(UUID.fromString("b4e5c42e-7e6b-11ec-90d6-0242ac120003"))
                    .name("John Doe")
                    .build()
            )
            .product(OrderProductResponse.builder()
                    .code(UUID.fromString("a3e5b2e8-7e6b-11ec-90d6-0242ac120003"))
                    .name("Laptop")
                    .build()
            )
            .build();
    private static final int AMOUNT_BIGGEST_THEN_STOCK = 1000;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void whenCallCreateOrderAndIsValidThenOrderResponse() throws IOException {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .clientCode(SOME_ORDER_RESPONSE.getClient().getCode().toString())
                .productCode(SOME_ORDER_RESPONSE.getProduct().getCode().toString())
                .amount(SOME_ORDER_RESPONSE.getAmount())
                .build();

        OrderResponse result = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .put(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderResponse.class);

        Assertions.assertEquals(SOME_ORDER_RESPONSE.getAmount(), result.getAmount());
        Assertions.assertEquals(SOME_ORDER_RESPONSE.getStatus(), result.getStatus());
        Assertions.assertEquals(SOME_ORDER_RESPONSE.getClient(), result.getClient());
        Assertions.assertEquals(SOME_ORDER_RESPONSE.getProduct(), result.getProduct());

        this.restartDatabase();
    }

    @Test
    public void whenCallCreateOrderButHaveInvalidAmountThenReturnConflictStatus() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .clientCode(SOME_ORDER_RESPONSE.getClient().getCode().toString())
                .productCode(SOME_ORDER_RESPONSE.getProduct().getCode().toString())
                .amount(this.faker.number().negative())
                .build();

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .put(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void whenCallCreateOrderButNoHaveSufficientStockThenReturnConflictStatus() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .clientCode(SOME_ORDER_RESPONSE.getClient().getCode().toString())
                .productCode(SOME_ORDER_RESPONSE.getProduct().getCode().toString())
                .amount(AMOUNT_BIGGEST_THEN_STOCK)
                .build();

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .put(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void whenCallCreateOrderButNotExistClientThenReturnNotFoundStatus() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .clientCode(ClientCode.create().getValue().toString())
                .productCode(SOME_ORDER_RESPONSE.getProduct().getCode().toString())
                .amount(AMOUNT_BIGGEST_THEN_STOCK)
                .build();

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .put(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenCallCreateOrderButNotExistProductThenReturnNotFoundStatus() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .clientCode(SOME_ORDER_RESPONSE.getClient().getCode().toString())
                .productCode(ProductCode.create().getValue().toString())
                .amount(AMOUNT_BIGGEST_THEN_STOCK)
                .build();

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .put(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
