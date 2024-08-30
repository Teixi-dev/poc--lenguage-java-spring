package teixi.dev.poc.client.domain.repositories;

import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.shared.domain.models.ClientCode;

import java.util.List;

public interface ClientRepository {
    public List<Client> searchAll();

    public Client find(ClientCode clientCode) throws ClientNotFoundException;
}
