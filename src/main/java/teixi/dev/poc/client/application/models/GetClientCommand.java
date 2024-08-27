package teixi.dev.poc.client.application.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetClientCommand {
    private String clientCode;
}
