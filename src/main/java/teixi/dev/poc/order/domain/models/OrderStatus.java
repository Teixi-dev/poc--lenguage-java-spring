package teixi.dev.poc.order.domain.models;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Pending"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    INVALID_STATUS("Invalid Status");

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

        return INVALID_STATUS;
    }
}
