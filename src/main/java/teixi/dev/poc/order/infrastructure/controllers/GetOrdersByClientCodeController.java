package teixi.dev.poc.order.infrastructure.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teixi.dev.poc.order.application.models.GetOrdersByClientCodeCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.application.services.GetOrdersByClientCodeUseCase;

import java.util.List;
import java.util.UUID;

@Controller
public class GetOrdersByClientCodeController {

    private final GetOrdersByClientCodeUseCase useCase;

    public GetOrdersByClientCodeController(GetOrdersByClientCodeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByClientCode(@RequestParam(name = "client") String clientCode) {
        GetOrdersByClientCodeCommand command = GetOrdersByClientCodeCommand.builder()
                .clientCode(UUID.fromString(clientCode))
                .build();

        return ResponseEntity.ok(this.useCase.execute(command));
    }
}
