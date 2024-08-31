package teixi.dev.poc.product.infrastructure.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import teixi.dev.poc.product.domain.exception.ProductNotFoundException;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;
import teixi.dev.poc.product.domain.repositories.ProductRepository;
import teixi.dev.poc.product.infrastructure.mappers.ProductRowMapper;
import teixi.dev.poc.shared.domain.exceptions.QueryNotFoundException;
import teixi.dev.poc.shared.domain.models.Query;
import teixi.dev.poc.shared.domain.models.QueryCode;
import teixi.dev.poc.shared.domain.repositories.QueryRepository;

import java.util.List;

@Repository
public class MySqlProductRepository implements ProductRepository {
    private static final QueryCode SEARCH_ALL_PRODUCTS_QUERY_CODE = QueryCode.builder()
            .value("searchAllProducts")
            .build();
    private static final QueryCode FIND_PRODUCT_BY_CODE_QUERY_CODE = QueryCode.builder()
            .value("findProductByCode")
            .build();
    private static final QueryCode FIND_PRODUCTS_BY_CODES_QUERY_CODE = QueryCode.builder()
            .value("findProductsByCodes")
            .build();
    private static final QueryCode SAVE_PRODUCT_QUERY_CODE = QueryCode.builder()
            .value("saveProduct")
            .build();
    private static final String PRODUCT_CODE_QUERY_PARAM = "productCode";
    private static final String PRODUCT_NAME_QUERY_PARAM = "productName";
    private static final String PRODUCT_DETAIL_QUERY_PARAM = "productDetail";
    private static final String PRODUCT_STOCK_QUERY_PARAM = "productStock";
    private static final String LIST_OF_PRODUCT_CODE_QUERY_PARAM = "productsCodes";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductRowMapper mapper;
    private final Query searchAll;
    private final Query findProductByCode;
    private final Query findProductsByCodes;
    private final Query saveProduct;

    public MySqlProductRepository(
            NamedParameterJdbcTemplate jdbcTemplate,
            ProductRowMapper mapper,
            QueryRepository queryRepository
    ) throws QueryNotFoundException {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.searchAll = queryRepository.load(SEARCH_ALL_PRODUCTS_QUERY_CODE);
        this.findProductByCode = queryRepository.load(FIND_PRODUCT_BY_CODE_QUERY_CODE);
        this.findProductsByCodes = queryRepository.load(FIND_PRODUCTS_BY_CODES_QUERY_CODE);
        this.saveProduct = queryRepository.load(SAVE_PRODUCT_QUERY_CODE);
    }

    @Override
    public List<Product> searchAll() {
        return this.jdbcTemplate.query(searchAll.getValue(), mapper);
    }

    @Override
    public Product find(ProductCode productCode) {
        try {
            return this.jdbcTemplate.queryForObject(
                    findProductByCode.getValue(),
                    new MapSqlParameterSource(PRODUCT_CODE_QUERY_PARAM, productCode.getValue().toString()),
                    mapper
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(productCode);
        }
    }

    @Override
    public List<Product> find(List<ProductCode> productsCode) {
        return this.jdbcTemplate.query(
                findProductsByCodes.getValue(),
                new MapSqlParameterSource(
                        LIST_OF_PRODUCT_CODE_QUERY_PARAM,
                        productsCode.stream().map(productCode ->
                                productCode.getValue().toString()
                        ).toList()
                ),
                mapper
        );
    }

    @Override
    public void save(Product product) {
        this.jdbcTemplate.update(
                this.saveProduct.getValue(),
                new MapSqlParameterSource()
                        .addValue(PRODUCT_CODE_QUERY_PARAM, product.getCode().getValue().toString())
                        .addValue(PRODUCT_NAME_QUERY_PARAM, product.getName())
                        .addValue(PRODUCT_DETAIL_QUERY_PARAM, product.getDetail())
                        .addValue(PRODUCT_STOCK_QUERY_PARAM, product.getStock())
        );
    }
}
