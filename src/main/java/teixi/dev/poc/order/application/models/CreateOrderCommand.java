package teixi.dev.poc.order.application.models;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CreateOrderCommand {
    private UUID clientCode;
    private UUID productCode;
    private int amount;
}
