package teixi.dev.poc.product.domain.exception;

import org.springframework.http.HttpStatus;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;
import teixi.dev.poc.shared.domain.models.ProductCode;

public class InsufficientProductStockException extends RuntimeDomainViolation {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String DESCRIPTION = "Insufficient stock of the product with code: '%s'";

    public InsufficientProductStockException(ProductCode code) {
        super(HTTP_STATUS, String.format(DESCRIPTION, code.getValue().toString()));
    }
}
