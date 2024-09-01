package teixi.dev.poc.client.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teixi.dev.poc.client.application.mappers.ClientResponseMapper;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.client.domain.repositories.ClientRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class GetClientListUseCaseTest {
    private final ClientRepository repository = Mockito.mock(ClientRepository.class);
    private final ClientResponseMapper mapper = new ClientResponseMapper();
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();
    private final GetClientListUseCase useCase = new GetClientListUseCase(repository, mapper);

    private static final int EXPECTED_EMPTY_LIST_SIZE = 0;

    @Test
    public void whenExecuteAndHaveClientsThenReturnClientResponseList() {
        List<Client> clients = this.clientTestDataGenerator.generateClientList();

        Mockito.when(this.repository.searchAll())
                .thenReturn(clients);

        List<ClientResponse> result = this.useCase.execute();

        Assertions.assertEquals(clients.size(), result.size());

        IntStream.range(0, clients.size()).forEach(i -> {
            Client client = clients.get(i);
            ClientResponse clientResponse = result.get(i);

            Assertions.assertEquals(client.getCode().getValue().toString(), clientResponse.getCode());
            Assertions.assertEquals(client.getName(), clientResponse.getName());
            Assertions.assertEquals(client.getPhone(), clientResponse.getPhone());
        });
    }

    @Test
    public void whenExecuteButNotHaveProductsThenReturnEmptyList() {
        Mockito.when(this.repository.searchAll())
                .thenReturn(Collections.emptyList());

        List<ClientResponse> result = this.useCase.execute();

        Assertions.assertEquals(EXPECTED_EMPTY_LIST_SIZE, result.size());
    }
}
