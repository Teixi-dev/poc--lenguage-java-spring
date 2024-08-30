package teixi.dev.poc.order.infrastructure.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import teixi.dev.poc.order.domain.exceptions.OrderNotFoundException;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.order.domain.repositories.OrderRepository;
import teixi.dev.poc.order.infrastructure.mappers.OrderRowMapper;
import teixi.dev.poc.shared.domain.models.ClientCode;
import teixi.dev.poc.shared.domain.models.OrderCode;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.repositories.QueryRepository;

import java.io.IOException;
import java.util.List;

@Repository
public class MySqlOrderRepository implements OrderRepository {
    private static final String FIND_BY_CODE_QUERY_PATH = "findOrderByCode";
    private static final String SEARCH_BY_CLIENT_CODE_QUERY_PATH = "searchOrderByClientCode";
    private static final String SAVE_ORDER_QUERY_PATH = "saveOrder";
    private static final String ORDER_CODE_QUERY_PARAM = "orderCode";
    private static final String CLIENT_CODE_QUERY_PARAM = "clientCode";
    private static final String PRODUCT_CODE_QUERY_PARAM = "productCode";
    private static final String AMOUNT_QUERY_PARAM = "amount";
    private static final String STATUS_QUERY_PARAM = "status";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper mapper;
    private final Query findByCode;
    private final Query searchOrderByClientCode;
    private final Query saveOrder;

    public MySqlOrderRepository(
            NamedParameterJdbcTemplate jdbcTemplate,
            OrderRowMapper mapper,
            QueryRepository queryRepository
    ) throws IOException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.findByCode = queryRepository.load(FIND_BY_CODE_QUERY_PATH);
        this.searchOrderByClientCode = queryRepository.load(SEARCH_BY_CLIENT_CODE_QUERY_PATH);
        this.saveOrder = queryRepository.load(SAVE_ORDER_QUERY_PATH);
    }

    @Override
    public List<Order> searchByClientCode(ClientCode clientCode) {
        return this.jdbcTemplate.query(
                this.searchOrderByClientCode.getValue(),
                new MapSqlParameterSource(CLIENT_CODE_QUERY_PARAM, clientCode.getValue().toString()),
                this.mapper
        );
    }

    @Override
    public Order find(OrderCode orderCode) throws OrderNotFoundException {
        try {
            return this.jdbcTemplate.queryForObject(
                    findByCode.getValue(),
                    new MapSqlParameterSource(ORDER_CODE_QUERY_PARAM, orderCode.getValue().toString()),
                    mapper
            );
        } catch (EmptyResultDataAccessException e) {
            throw new OrderNotFoundException(orderCode);
        }
    }

    @Override
    public void save(Order order) {
        this.jdbcTemplate.update(
                this.saveOrder.getValue(),
                new MapSqlParameterSource()
                        .addValue(ORDER_CODE_QUERY_PARAM, order.getCode().getValue().toString())
                        .addValue(CLIENT_CODE_QUERY_PARAM, order.getClientCode().getValue().toString())
                        .addValue(PRODUCT_CODE_QUERY_PARAM, order.getProductCode().getValue().toString())
                        .addValue(AMOUNT_QUERY_PARAM, order.getAmount())
                        .addValue(STATUS_QUERY_PARAM, order.getStatus().toString())
        );
    }
}