package teixi.dev.poc.client.insfrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.application.models.GetClientCommand;
import teixi.dev.poc.client.application.services.GetClientUseCase;

import java.util.UUID;

@Controller
public class GetClientController {
    private final GetClientUseCase useCase;

    public GetClientController(GetClientUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "clients/{clientCode}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String clientCode) {
        GetClientCommand command = GetClientCommand.builder()
                .clientCode(UUID.fromString(clientCode))
                .build();

        ClientResponse clientResponse = this.useCase.execute(command);

        return ResponseEntity.ok(clientResponse);
    }
}
