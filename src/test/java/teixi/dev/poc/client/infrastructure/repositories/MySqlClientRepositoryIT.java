package teixi.dev.poc.client.infrastructure.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.insfrastructure.repositories.MySqlClientRepository;
import teixi.dev.poc.shared.infrastructure.services.IntegrationTestWithApplicationContainers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MySqlClientRepositoryIT extends IntegrationTestWithApplicationContainers {
    public static final String DELETE_FROM_CLIENTS = "DELETE FROM clients";
    @Autowired
    private MySqlClientRepository repository;

    private static final Client SOME_CLIENT = Client.builder()
            .code(ClientCode.builder().value(UUID.fromString("b4e5c42e-7e6b-11ec-90d6-0242ac120003")).build())
            .name("John Doe")
            .phone("555-1234")
            .build();
    private static final Client OTHER_CLIENT = Client.builder()
            .code(ClientCode.builder().value(UUID.fromString("b4e5c9ec-7e6b-11ec-90d6-0242ac120003")).build())
            .name("Jane Smith")
            .phone("555-5678")
            .build();
    private static final int EXPECTED_SEARCH_ALL_SIZE = 2;
    private static final int EXPECTED_SEARCH_ALL_EMPTY_SIZE = 0;

    @Test
    public void whenFindAndExistThenReturnClient() {
        Client client = this.repository.find(SOME_CLIENT.getCode());

        Assertions.assertEquals(SOME_CLIENT, client);
    }

    @Test
    public void whenFindAndNotExistThenReturnClient() {
        ClientCode clientCode = ClientCode.create();

        Assertions.assertThrows(
                ClientNotFoundException.class,
                () -> this.repository.find(clientCode)
        );
    }

    @Test
    public void whenSearchAllAndHaveClientsThenReturnClientList() {
        List<Client> clients = this.repository.searchAll();

        Assertions.assertEquals(EXPECTED_SEARCH_ALL_SIZE, clients.size());
        Assertions.assertTrue(clients.containsAll(List.of(SOME_CLIENT, OTHER_CLIENT)));
    }

    @Test
    public void whenSearchAllButNotHaveClientsThenReturnClientList() throws IOException {
        jdbcTemplate.execute(DELETE_FROM_CLIENTS);

        List<Client> clients = this.repository.searchAll();

        Assertions.assertEquals(EXPECTED_SEARCH_ALL_EMPTY_SIZE, clients.size());

        this.restartDatabase();
    }
}
