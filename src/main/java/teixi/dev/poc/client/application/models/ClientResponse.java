package teixi.dev.poc.client.application.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientResponse {
    private String code;
    private String name;
}
