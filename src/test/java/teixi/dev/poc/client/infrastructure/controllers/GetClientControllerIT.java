package teixi.dev.poc.client.infrastructure.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

public class GetClientControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private static final String URL_TEMPLATE = "/clients/{clientsCode}";
    private static final String BASE_URL = "http://localhost";
    private static final ClientResponse SOME_CLIENT = ClientResponse.builder()
            .code("b4e5c42e-7e6b-11ec-90d6-0242ac120003")
            .name("John Doe")
            .phone("555-1234")
            .build();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void whenCallGetClientAndExistThenReturnClientResponse() {
        ClientResponse result = RestAssured.given()
                .when()
                .get(
                        URL_TEMPLATE,
                        SOME_CLIENT.getCode()
                )
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ClientResponse.class);

        Assertions.assertEquals(SOME_CLIENT, result);
    }

    @Test
    public void whenCallGetClientAndNotExistThenReturnNotFoundStatus() {
        RestAssured.given()
                .when()
                .get(
                        URL_TEMPLATE,
                        ClientCode.create().getValue().toString()
                )
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
