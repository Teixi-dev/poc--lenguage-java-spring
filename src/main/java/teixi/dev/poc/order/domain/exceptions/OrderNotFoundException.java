package teixi.dev.poc.order.domain.exceptions;

import org.springframework.http.HttpStatus;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;
import teixi.dev.poc.shared.domain.models.OrderCode;

public class OrderNotFoundException extends RuntimeDomainViolation {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String DESCRIPTION = "Not found order with following code: '%s'";

    public OrderNotFoundException(OrderCode orderCode) {
        super(HTTP_STATUS, String.format(DESCRIPTION, orderCode.getValue().toString()));
    }
}
