package teixi.dev.poc.client.insfrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.application.services.GetClientListUseCase;

import java.util.List;

@Controller
public class GetClientListController {
    private final GetClientListUseCase useCase;

    public GetClientListController(GetClientListUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<List<ClientResponse>> geClientList() {
        return ResponseEntity.ok(this.useCase.execute());
    }
}