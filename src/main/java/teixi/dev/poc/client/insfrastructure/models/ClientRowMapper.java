package teixi.dev.poc.client.insfrastructure.models;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class ClientRowMapper implements RowMapper<Client> {
    private static final String CODE_COLUMN = "code";
    private static final String NAME_COLUMN = "name";

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Client.builder()
                .code(ClientCode.builder()
                        .value(UUID.fromString(rs.getString(CODE_COLUMN)))
                        .build()
                )
                .name(rs.getString(NAME_COLUMN))
                .build();
    }
}
