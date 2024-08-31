package teixi.dev.poc.shared.domain.exceptions;

import teixi.dev.poc.shared.domain.models.QueryCode;

public class QueryNotFoundException extends DomainViolation {
    private static final String DESCRIPTION = "The query with code %s not exist.";

    public QueryNotFoundException(QueryCode code) {
        super(String.format(DESCRIPTION, code.getValue()));
    }
}
