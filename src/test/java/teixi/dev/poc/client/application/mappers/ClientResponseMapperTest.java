package teixi.dev.poc.client.application.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import teixi.dev.poc.client.application.models.ClientResponse;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientTestDataGenerator;

public class ClientResponseMapperTest {
    private final ClientResponseMapper mapper = new ClientResponseMapper();
    private final ClientTestDataGenerator clientTestDataGenerator = new ClientTestDataGenerator();

    @Test
    public void WhenMapCorrectClientThenReturnClientResponse() {
        Client client = this.clientTestDataGenerator.generateClient();

        ClientResponse result = this.mapper.map(client);

        Assertions.assertEquals(client.getCode().getValue().toString(), result.getCode());
        Assertions.assertEquals(client.getName(), result.getName());
        Assertions.assertEquals(client.getPhone(), result.getPhone());
    }
}
