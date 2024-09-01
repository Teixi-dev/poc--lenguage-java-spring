package teixi.dev.poc.client.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Client {
    private ClientCode code;
    private String phone;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(code, client.code) && Objects.equals(phone, client.phone) && Objects.equals(name, client.name);
    }
}
