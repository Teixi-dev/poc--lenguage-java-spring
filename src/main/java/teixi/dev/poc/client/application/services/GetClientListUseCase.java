package teixi.dev.poc.client.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.repositories.ClientRepository;

import java.util.List;

@Service
public class GetClientListUseCase {

    private final ClientRepository repository;

    public GetClientListUseCase(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> execute() {
        return repository.searchAll();
    }
}
