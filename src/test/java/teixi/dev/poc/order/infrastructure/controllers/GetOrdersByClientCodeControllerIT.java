package teixi.dev.poc.order.infrastructure.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.order.application.models.response.OrderClientResponse;
import teixi.dev.poc.order.application.models.response.OrderProductResponse;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GetOrdersByClientCodeControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private static final int EXPECTED_SEARCH_ALL_EMPTY_SIZE = 0;
    private static final String EMPTY_STRING = "";
    private static final String CLIENT_PARAM_KEY = "client";
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String URL_TEMPLATE = "/orders";
    private static final String BASE_URL = "http://localhost";
    private static final String CLIENT_CODE_WITH_OUT_ORDER = "538e2695-8b66-4bca-8b57-93d18f746be4";
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
    private static final OrderResponse OTHER_ORDER_RESPONSE = OrderResponse.builder()
            .code(UUID.fromString("c5e6d66c-7e6b-11ec-90d6-0242ac120003"))
            .amount(2)
            .status("Shipped")
            .updatedAt(fromString("2014-05-24 10:30:10"))
            .createdAt(fromString("2014-05-21 10:30:10"))
            .client(OrderClientResponse.builder()
                    .code(UUID.fromString("b4e5c42e-7e6b-11ec-90d6-0242ac120003"))
                    .name("John Doe")
                    .build()
            )
            .product(OrderProductResponse.builder()
                    .code(UUID.fromString("a3e5b8e6-7e6b-11ec-90d6-0242ac120003"))
                    .name("Smartphone")
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
        List<OrderResponse> result = RestAssured.given()
                .param(CLIENT_PARAM_KEY, SOME_ORDER_RESPONSE.getClient().getCode().toString())
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(EMPTY_STRING, OrderResponse.class);

        Assertions.assertTrue(result.containsAll(List.of(SOME_ORDER_RESPONSE, OTHER_ORDER_RESPONSE)));
    }

    @Test
    public void whenCallGetOrderByClientCodeButNotExistThenReturnNotFoundStatus() {
        RestAssured.given()
                .param(CLIENT_PARAM_KEY, ClientCode.create().getValue().toString())
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenCallGetOrderButNotExistThenReturnNotFoundStatus() {
        List<OrderResponse> result = RestAssured.given()
                .param(CLIENT_PARAM_KEY, CLIENT_CODE_WITH_OUT_ORDER)
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(EMPTY_STRING, OrderResponse.class);

        Assertions.assertEquals(EXPECTED_SEARCH_ALL_EMPTY_SIZE, result.size());
    }

    private static Date fromString(String dateString) {
        try {
            return FORMAT_DATE.parse(dateString);
        } catch (ParseException e) {
            throw new InvalidParameterException(e);
        }
    }
}