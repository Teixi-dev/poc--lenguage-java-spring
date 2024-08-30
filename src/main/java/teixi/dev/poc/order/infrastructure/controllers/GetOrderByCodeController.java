package teixi.dev.poc.order.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import teixi.dev.poc.order.application.models.GetOrderByCodeCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.application.services.GetOrderByCodeUseCase;

import java.util.UUID;

@Controller
public class GetOrderByCodeController {
    private final GetOrderByCodeUseCase useCase;

    public GetOrderByCodeController(GetOrderByCodeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/orders/{orderCode}")
    public ResponseEntity<OrderResponse> getOrderByCode(@PathVariable String orderCode) {
        GetOrderByCodeCommand command = GetOrderByCodeCommand.builder()
                .orderCode(UUID.fromString(orderCode))
                .build();

        return ResponseEntity.ok(this.useCase.execute(command));
    }
}
