package teixi.dev.poc.client.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.application.mappers.ClientResponseMapper;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.application.models.GetClientCommand;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.domain.repositories.ClientRepository;

@Service
public class GetClientUseCase {
    private final ClientRepository repository;
    private final ClientResponseMapper mapper;

    public GetClientUseCase(
            ClientRepository repository,
            ClientResponseMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ClientResponse execute(GetClientCommand command) {
        ClientCode clientCode = ClientCode.builder()
                .value(command.getClientCode())
                .build();

        Client client = this.repository.find(clientCode);

        return this.mapper.map(client);
    }
}
