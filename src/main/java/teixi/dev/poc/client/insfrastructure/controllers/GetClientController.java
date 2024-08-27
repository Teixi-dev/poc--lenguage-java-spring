package teixi.dev.poc.client.insfrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.client.application.models.GetClientCommand;
import teixi.dev.poc.client.application.services.GetClientUseCase;
import teixi.dev.poc.client.insfrastructure.mappers.ClientViewMapper;
import teixi.dev.poc.client.insfrastructure.models.ClientView;

@Controller
public class GetClientController {

    private final GetClientUseCase useCase;
    private final ClientViewMapper mapper;

    public GetClientController(
            GetClientUseCase useCase,
            ClientViewMapper mapper
    ) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping(value = "clients/{clientCode}")
    public ResponseEntity<ClientView> getClient(@PathVariable String clientCode) {
        GetClientCommand command = GetClientCommand.builder()
                .clientCode(clientCode)
                .build();

        return ResponseEntity.ok(this.mapper.map(this.useCase.execute(command)));
    }
}
