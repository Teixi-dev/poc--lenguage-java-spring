package teixi.dev.poc.client.domain.models;

import lombok.Builder;
import lombok.Getter;
import teixi.dev.poc.shared.domain.models.ClientCode;

@Getter
@Builder
public class Client {
    private ClientCode code;
    private String name;
}
