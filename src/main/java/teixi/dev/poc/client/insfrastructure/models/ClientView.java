package teixi.dev.poc.client.insfrastructure.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientView {
    private String code;
    private String name;
}
