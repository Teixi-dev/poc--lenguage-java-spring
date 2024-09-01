package teixi.dev.poc.client.application.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String code;
    private String name;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientResponse that)) return false;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, phone);
    }
}
