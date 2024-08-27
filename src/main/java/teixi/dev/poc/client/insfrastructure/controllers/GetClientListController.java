package teixi.dev.poc.client.insfrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.client.application.services.GetClientListUseCase;
import teixi.dev.poc.client.insfrastructure.mappers.ClientViewMapper;
import teixi.dev.poc.client.insfrastructure.models.ClientView;

import java.util.List;

@Controller
public class GetClientListController {
    private final GetClientListUseCase useCase;
    private final ClientViewMapper mapper;

    public GetClientListController(GetClientListUseCase useCase, ClientViewMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<List<ClientView>> geClientList() {
        return ResponseEntity.ok(this.useCase.execute().stream().map(this.mapper::map).toList());
    }
}