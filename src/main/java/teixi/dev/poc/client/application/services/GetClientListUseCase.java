package teixi.dev.poc.client.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.application.mappers.ClientResponseMapper;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.domain.repositories.ClientRepository;

import java.util.List;

@Service
public class GetClientListUseCase {
    private final ClientRepository repository;
    private final ClientResponseMapper mapper;

    public GetClientListUseCase(ClientRepository repository, ClientResponseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClientResponse> execute() {
        return repository.searchAll().stream().map(mapper::map).toList();
    }
}
