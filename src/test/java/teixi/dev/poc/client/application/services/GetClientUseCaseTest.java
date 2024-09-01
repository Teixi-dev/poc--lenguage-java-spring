package teixi.dev.poc.client.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import teixi.dev.poc.client.application.mappers.ClientResponseMapper;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.application.models.GetClientCommand;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;
import teixi.dev.poc.client.domain.repositories.ClientRepository;

public class GetClientUseCaseTest {
    private final ClientRepository repository = Mockito.mock(ClientRepository.class);
    private final ClientResponseMapper mapper = new ClientResponseMapper();
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();
    private final GetClientUseCase useCase = new GetClientUseCase(repository, mapper);

    @Test
    public void whenExecuteAndExistClientCodeThenReturnClientResponse() {
        Client client = this.clientTestDataGenerator.generateClient();

        GetClientCommand command = GetClientCommand.builder()
                .clientCode(client.getCode().getValue())
                .build();

        Mockito.when(this.repository.find(client.getCode()))
                .thenReturn(client);

        ClientResponse result = this.useCase.execute(command);

        Assertions.assertEquals(client.getCode().getValue().toString(), result.getCode());
        Assertions.assertEquals(client.getName(), result.getName());
        Assertions.assertEquals(client.getPhone(), result.getPhone());
    }

    @Test
    public void whenExecuteAndNotExistClientThenThrowClientNotFoundException() {
        ClientCode clientCode = this.clientTestDataGenerator.generateClientCode();

        GetClientCommand command = GetClientCommand.builder()
                .clientCode(clientCode.getValue())
                .build();

        Mockito.when(this.repository.find(clientCode))
                .thenThrow(new ClientNotFoundException(clientCode));

        Assertions.assertThrows(
                ClientNotFoundException.class,
                () -> this.useCase.execute(command)
        );
    }
}
