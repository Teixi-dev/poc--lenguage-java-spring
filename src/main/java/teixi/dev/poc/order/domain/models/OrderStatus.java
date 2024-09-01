package teixi.dev.poc.order.domain.models;

import lombok.Getter;
import teixi.dev.poc.order.domain.exceptions.InvalidOrderStatusException;

@Getter
public enum OrderStatus {
    PENDING("Pending"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered");
    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static OrderStatus fromValue(String value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidOrderStatusException(value);
    }
}
