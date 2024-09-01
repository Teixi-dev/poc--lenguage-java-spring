package teixi.dev.poc.client.application.models;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetClientCommand {
    private UUID clientCode;
}
