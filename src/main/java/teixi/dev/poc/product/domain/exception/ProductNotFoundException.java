package teixi.dev.poc.product.domain.exception;

import org.springframework.http.HttpStatus;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;
import teixi.dev.poc.product.domain.models.ProductCode;

public class ProductNotFoundException extends RuntimeDomainViolation {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String DESCRIPTION = "Not found product with following code: '%s'";

    public ProductNotFoundException(ProductCode productCode) {
        super(HTTP_STATUS, String.format(DESCRIPTION, productCode.getValue().toString()));
    }
}
