package teixi.dev.poc.client.insfrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.client.application.models.GetClientCommand;
import teixi.dev.poc.client.application.services.GetClientUseCase;
import teixi.dev.poc.client.application.mappers.ClientResponseMapper;
import teixi.dev.poc.client.application.models.ClientResponse;

@Controller
public class GetClientController {
    private final GetClientUseCase useCase;
    private final ClientResponseMapper mapper;

    public GetClientController(
            GetClientUseCase useCase,
            ClientResponseMapper mapper
    ) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping(value = "clients/{clientCode}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String clientCode) {
        GetClientCommand command = GetClientCommand.builder()
                .clientCode(clientCode)
                .build();

        return ResponseEntity.ok(this.mapper.map(this.useCase.execute(command)));
    }
}
