package teixi.dev.poc.shared.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;

@ControllerAdvice
public class ControlAdviceController {

    @ExceptionHandler({RuntimeDomainViolation.class})
    public ResponseEntity<String> runtimeDomainViolationHandler(RuntimeDomainViolation runtimeDomainViolation) {

        return ResponseEntity
                .status(runtimeDomainViolation.getHttpStatus())
                .body(runtimeDomainViolation.getDescription());
    }
}
