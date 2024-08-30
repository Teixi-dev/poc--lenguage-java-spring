package teixi.dev.poc.order.domain.exceptions;

import org.springframework.http.HttpStatus;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;
import teixi.dev.poc.order.domain.models.OrderCode;
import teixi.dev.poc.order.domain.models.OrderStatus;

public class OrderAdvanceStatusException extends RuntimeDomainViolation {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String DESCRIPTION = "Can't advance the status %s of the order with code: '%s'";

    public OrderAdvanceStatusException(OrderCode code, OrderStatus status) {
        super(HTTP_STATUS, String.format(DESCRIPTION, status.getValue(), code.getValue().toString()));
    }
}
