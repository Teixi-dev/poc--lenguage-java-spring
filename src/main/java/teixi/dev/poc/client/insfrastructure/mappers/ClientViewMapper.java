package teixi.dev.poc.client.insfrastructure.mappers;

import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.insfrastructure.models.ClientView;

@Service
public class ClientViewMapper {

    public ClientView map(Client client) {
        return ClientView.builder()
                .code(client.getCode().getValue().toString())
                .name(client.getName())
                .build();
    }
}
