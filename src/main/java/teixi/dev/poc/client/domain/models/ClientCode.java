package teixi.dev.poc.client.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ClientCode {
    private UUID value;
}