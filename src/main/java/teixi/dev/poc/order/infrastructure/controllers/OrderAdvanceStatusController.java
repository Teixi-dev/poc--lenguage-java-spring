package teixi.dev.poc.order.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import teixi.dev.poc.order.application.models.OrderAdvanceStatusCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.application.services.OrderAdvanceStatusUseCase;

import java.util.UUID;

@Controller
public class OrderAdvanceStatusController {
    private final OrderAdvanceStatusUseCase useCase;

    public OrderAdvanceStatusController(OrderAdvanceStatusUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping(value = "/orders/{orderCode}/advance")
    public ResponseEntity<OrderResponse> orderAdvanceStatus(@PathVariable String orderCode) {
        OrderAdvanceStatusCommand command = OrderAdvanceStatusCommand.builder()
                .orderCode(UUID.fromString(orderCode))
                .build();

        return ResponseEntity.ok(this.useCase.execute(command));
    }
}
