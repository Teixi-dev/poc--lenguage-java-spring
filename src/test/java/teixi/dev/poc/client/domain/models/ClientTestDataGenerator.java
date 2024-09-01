package teixi.dev.poc.client.domain.models;

import net.datafaker.Faker;

import java.util.List;
import java.util.stream.IntStream;

public class ClientTestDataGenerator {
    private final Faker faker = new Faker();

    public static final int ZERO_VALUE = 0;
    public static final int MIN_NUMBER_OF_CLIENTS = 2;
    public static final int MAX_NUMBER_OF_CLIENTS = 10;
    
    public Client generateClient() {
        return Client.builder()
                .code(this.generateClientCode())
                .name(this.faker.name().name())
                .phone(this.faker.phoneNumber().cellPhone())
                .build();
    }

    public ClientCode generateClientCode() {
        return ClientCode.create();
    }

    public List<Client> generateClientList() {
        int numberOfProducts = this.faker.number().numberBetween(MIN_NUMBER_OF_CLIENTS, MAX_NUMBER_OF_CLIENTS);

        return IntStream.range(ZERO_VALUE, numberOfProducts)
                .mapToObj(_ -> this.generateClient())
                .toList();
    }
}
