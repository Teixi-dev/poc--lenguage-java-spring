package teixi.dev.poc.client.insfrastructure.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import teixi.dev.poc.client.domain.exceptions.ClientNotFoundException;
import teixi.dev.poc.client.domain.models.Client;
import teixi.dev.poc.client.domain.models.ClientCode;
import teixi.dev.poc.client.domain.repositories.ClientRepository;
import teixi.dev.poc.client.insfrastructure.mappers.ClientRowMapper;
import teixi.dev.poc.shared.domain.exceptions.QueryNotFoundException;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.models.QueryCode;
import teixi.dev.poc.shared.domain.repositories.QueryRepository;

import java.util.List;

@Repository
public class MySqlClientRepository implements ClientRepository {
    private static final QueryCode SEARCH_ALL_CLIENTS_QUERY_CODE = QueryCode.builder()
            .value("searchAllClients")
            .build();
    private static final QueryCode FIND_CLIENT_BY_CODE_QUERY_CODE = QueryCode.builder()
            .value("findClientByCode")
            .build();
    private static final String CLIENT_CODE_QUERY_PARAM = "clientCode";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ClientRowMapper mapper;
    private final Query searchAll;
    private final Query findProductByCode;

    public MySqlClientRepository(
            NamedParameterJdbcTemplate jdbcTemplate,
            ClientRowMapper mapper,
            QueryRepository queryRepository
    ) throws QueryNotFoundException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.searchAll = queryRepository.load(SEARCH_ALL_CLIENTS_QUERY_CODE);
        this.findProductByCode = queryRepository.load(FIND_CLIENT_BY_CODE_QUERY_CODE);
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
