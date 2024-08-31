package teixi.dev.poc.shared.domain.exceptions;

public class DomainViolation extends Exception {

    public DomainViolation(String description) {
        super(description);
    }
}
