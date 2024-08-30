package teixi.dev.poc.client.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.application.models.GetClientCommand;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.client.domain.models.ClientCode;

import java.util.UUID;

@Service
public class GetClientUseCase {

    private final ClientRepository repository;

    public GetClientUseCase(ClientRepository repository) {
        this.repository = repository;
    }

    public Client execute(GetClientCommand command) {
        ClientCode clientCode = ClientCode.builder()
                .value(UUID.fromString(command.getClientCode()))
                .build();

        return this.repository.find(clientCode);
    }
}
