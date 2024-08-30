package teixi.dev.poc.client.domain.exceptions;

import org.springframework.http.HttpStatus;
import teixi.dev.poc.shared.domain.exceptions.RuntimeDomainViolation;
import teixi.dev.poc.client.domain.models.ClientCode;

public class ClientNotFoundException extends RuntimeDomainViolation {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String DESCRIPTION = "Not found client with following code: '%s'";

    public ClientNotFoundException(ClientCode clientCode) {
        super(HTTP_STATUS, String.format(DESCRIPTION, clientCode.getValue().toString()));
    }
}
