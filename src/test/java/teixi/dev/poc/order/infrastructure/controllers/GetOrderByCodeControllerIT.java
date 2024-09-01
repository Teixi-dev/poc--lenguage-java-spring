package teixi.dev.poc.order.infrastructure.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.order.application.models.response.OrderClientResponse;
import teixi.dev.poc.order.application.models.response.OrderProductResponse;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.OrderCode;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GetOrderByCodeControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String URL_TEMPLATE = "/orders/{orderCode}";
    private static final String BASE_URL = "http://localhost";
    private static final OrderResponse SOME_ORDER_RESPONSE = OrderResponse.builder()
            .code(UUID.fromString("c5e6d34e-7e6b-11ec-90d6-0242ac120003"))
            .amount(1)
            .status("Pending")
            .updatedAt(fromString("2014-05-28 15:30:10"))
            .createdAt(fromString("2014-05-21 13:30:10"))
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

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void whenCallGetOrderAndExistThenReturnOrderResponse() {
        OrderResponse result = RestAssured.given()
                .when()
                .get(
                        URL_TEMPLATE,
                        SOME_ORDER_RESPONSE.getCode()
                )
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderResponse.class);

        Assertions.assertEquals(SOME_ORDER_RESPONSE, result);
    }

    @Test
    public void whenCallGetOrderButNotExistThenReturnNotFoundStatus() {
        RestAssured.given()
                .when()
                .get(
                        URL_TEMPLATE,
                        OrderCode.create().getValue().toString()
                )
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    private static Date fromString(String dateString) {
        try {
            return FORMAT_DATE.parse(dateString);
        } catch (ParseException e) {
            throw new InvalidParameterException(e);
        }
    }
}
