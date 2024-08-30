package teixi.dev.poc.client.domain.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Client {
    private ClientCode code;
    private String phone;
    private String name;
}
