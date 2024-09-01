package teixi.dev.poc.client.application.mappers;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.domain.models.Client;

@Service
public class ClientResponseMapper {

    public ClientResponse map(Client client) {
        return ClientResponse.builder()
                .code(client.getCode().getValue().toString())
                .name(client.getName())
                .phone(client.getPhone())
                .build();
    }
}
