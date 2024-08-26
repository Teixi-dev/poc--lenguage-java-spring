package teixi.dev.poc.shared.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RuntimeDomainViolation extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String description;

    public RuntimeDomainViolation(HttpStatus httpStatus, String description) {
        super(description);
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
