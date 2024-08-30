package teixi.dev.poc.order.application.services;

import org.springframework.stereotype.Service;
import teixi.dev.poc.order.application.models.GetOrderByCodeCommand;
import teixi.dev.poc.order.application.models.OrderAdvanceStatusCommand;
import teixi.dev.poc.order.application.models.response.OrderResponse;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.order.domain.models.OrderCode;

@Service
public class OrderAdvanceStatusUseCase {
    private final OrderRepository repository;
    private final GetOrderByCodeUseCase useCase;

    public OrderAdvanceStatusUseCase(
            OrderRepository repository,
            GetOrderByCodeUseCase useCase
    ) {
        this.repository = repository;
        this.useCase = useCase;
    }

    public OrderResponse execute(OrderAdvanceStatusCommand command) {
        OrderCode orderCode = OrderCode.builder()
                .value(command.getOrderCode())
                .build();

        Order order = this.repository.find(orderCode);
        Order advancedOrder = order.advanceStatus();

        this.repository.save(advancedOrder);

        return this.useCase.execute(GetOrderByCodeCommand.builder()
                .orderCode(advancedOrder.getCode().getValue())
                .build()
        );
    }
}
