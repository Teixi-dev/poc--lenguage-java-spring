package teixi.dev.poc.client.insfrastructure.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.client.insfrastructure.models.ClientRowMapper;
import teixi.dev.poc.shared.domain.models.ClientCode;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.repositories.QueryRepository;

import java.io.IOException;
import java.util.List;

@Repository
public class MySqlClientRepository implements ClientRepository {
    private static final String SEARCH_ALL_CLIENTS_QUERY_PATH = "searchAllClients";
    private static final String FIND_CLIENT_BY_CODE_QUERY_PATH = "findClientByCode";
    private static final String CLIENT_CODE_QUERY_PARAM = "clientCode";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ClientRowMapper mapper;
    private final Query searchAll;
    private final Query findProductByCode;

    public MySqlClientRepository(
            NamedParameterJdbcTemplate jdbcTemplate,
            ClientRowMapper mapper,
            QueryRepository queryRepository
    ) throws IOException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.searchAll = queryRepository.load(SEARCH_ALL_CLIENTS_QUERY_PATH);
        this.findProductByCode = queryRepository.load(FIND_CLIENT_BY_CODE_QUERY_PATH);
    }

    @Override
    public List<Client> searchAll() {
        return this.jdbcTemplate.query(searchAll.getValue(), mapper);
    }

    @Override
    public Client find(ClientCode clientCode) {
        try {
            return this.jdbcTemplate.queryForObject(
                    findProductByCode.getValue(),
                    new MapSqlParameterSource(CLIENT_CODE_QUERY_PARAM, clientCode.getValue().toString()),
                    mapper
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ClientNotFoundException(clientCode);
        }
    }
}
