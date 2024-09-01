package teixi.dev.poc.order.domain.exceptions;

import org.springframework.http.HttpStatus;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;

public class InvalidOrderStatusException extends RuntimeDomainViolation {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String DESCRIPTION = "The order could not be retrieved because the status has an incorrect value: %s";

    public InvalidOrderStatusException(String status) {
        super(HTTP_STATUS, String.format(DESCRIPTION, status));
    }
}