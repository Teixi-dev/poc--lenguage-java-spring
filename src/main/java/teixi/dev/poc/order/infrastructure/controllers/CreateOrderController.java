package teixi.dev.poc.order.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import teixi.dev.poc.order.application.models.CreateOrderCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.application.services.CreateOrderUseCase;
import teixi.dev.poc.order.infrastructure.models.CreateOrderRequest;

import java.util.UUID;

@Controller
public class CreateOrderController {
    private final CreateOrderUseCase useCase;

    public CreateOrderController(CreateOrderUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping(value = "/orders")
    public ResponseEntity<OrderResponse> getOrderByCode(@RequestBody CreateOrderRequest request) {
        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientCode(UUID.fromString(request.getClientCode()))
                .productCode(UUID.fromString(request.getProductCode()))
                .amount(request.getAmount())
                .build();

        return ResponseEntity.ok(this.useCase.execute(command));
    }
}
