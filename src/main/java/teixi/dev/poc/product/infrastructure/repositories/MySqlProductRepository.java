package teixi.dev.poc.product.infrastructure.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.repositories.ProductRepository;
import teixi.dev.poc.product.infrastructure.mappers.ProductRowMapper;
import teixi.dev.poc.shared.domain.models.ProductCode;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.repositories.QueryRepository;

import java.io.IOException;
import java.util.List;

@Repository
public class MySqlProductRepository implements ProductRepository {
    private static final String SEARCH_ALL_PRODUCTS_QUERY_PATH = "searchAllProductsQuery";
    private static final String FIND_PRODUCT_BY_CODE_QUERY_PATH = "findProductByCode";
    private static final String PRODUCT_CODE_QUERY_PARAM = "productCode";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductRowMapper mapper;
    private final Query searchAllProductsQuery;
    private final Query findProductByProductCode;

    public MySqlProductRepository(
            NamedParameterJdbcTemplate jdbcTemplate,
            ProductRowMapper mapper,
            QueryRepository queryRepository
    ) throws IOException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.searchAllProductsQuery = queryRepository.load(SEARCH_ALL_PRODUCTS_QUERY_PATH);
        this.findProductByProductCode = queryRepository.load(FIND_PRODUCT_BY_CODE_QUERY_PATH);
    }

    @Override
    public List<Product> searchAll() {
        return this.jdbcTemplate.query(searchAllProductsQuery.getValue(), mapper);
    }

    @Override
    public Product find(ProductCode productCode) {
        try {
            return this.jdbcTemplate.queryForObject(
                    findProductByProductCode.getValue(),
                    new MapSqlParameterSource(PRODUCT_CODE_QUERY_PARAM, productCode.getValue().toString()),
                    mapper
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(productCode);
        }
    }
}
