package teixi.dev.poc.client.infrastructure.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.io.IOException;
import java.util.List;

public class GetClientListControllerIT extends IntegrationTestWithApplicationContainers {
    @LocalServerPort
    private int port;

    private static final int EXPECTED_SEARCH_ALL_SIZE = 2;
    private static final int EXPECTED_SEARCH_ALL_EMPTY_SIZE = 0;
    private static final String URL_TEMPLATE = "/clients";
    private static final String BASE_URL = "http://localhost";
    public static final String DELETE_FROM_CLIENTS = "DELETE FROM clients";
    private static final String EMPTY_STRING = "";
    private static final ClientResponse SOME_CLIENT = ClientResponse.builder()
            .code("b4e5c42e-7e6b-11ec-90d6-0242ac120003")
            .name("John Doe")
            .phone("555-1234")
            .build();
    private static final ClientResponse OTHER_CLIENT = ClientResponse.builder()
            .code("b4e5c9ec-7e6b-11ec-90d6-0242ac120003")
            .name("Jane Smith")
            .phone("555-5678")
            .build();

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void whenCallGetClientAndExistThenReturnClientResponse() {

        List<ClientResponse> result = RestAssured.given()
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(EMPTY_STRING, ClientResponse.class);

        Assertions.assertEquals(EXPECTED_SEARCH_ALL_SIZE, result.size());
        Assertions.assertTrue(result.containsAll(List.of(SOME_CLIENT, OTHER_CLIENT)));
    }

    @Test
    public void whenCallGetClientAndNotExistThenReturnNotFoundStatus() throws IOException {
        jdbcTemplate.execute(DELETE_FROM_CLIENTS);

        List<ClientResponse> result = RestAssured.given()
                .when()
                .get(URL_TEMPLATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(EMPTY_STRING, ClientResponse.class);

        Assertions.assertEquals(EXPECTED_SEARCH_ALL_EMPTY_SIZE, result.size());

        this.restartDatabase();
    }
}
