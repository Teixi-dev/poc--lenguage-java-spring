package teixi.dev.poc.shared.infrastructure.services;

import java.time.Instant;
import java.util.UUID;

public class UniqueUUIDGenerator {

    public static UUID generate() {
        return UUID.nameUUIDFromBytes(Instant.now().toString().getBytes());
    }
}
