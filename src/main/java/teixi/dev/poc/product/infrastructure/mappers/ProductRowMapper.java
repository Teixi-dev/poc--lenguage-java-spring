package teixi.dev.poc.product.infrastructure.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.shared.domain.models.ProductCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class ProductRowMapper implements RowMapper<Product> {
    private static final String CODE_COLUMN_NAME = "code";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String DETAIL_COLUMN_NAME = "detail";
    private static final String PRICE_COLUMN_NAME = "price";
    private static final String STOCK_COLUMN_NAME = "stock";

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .code(ProductCode.builder()
                        .value(UUID.fromString(rs.getString(CODE_COLUMN_NAME)))
                        .build()
                )
                .name(rs.getString(NAME_COLUMN_NAME))
                .detail(rs.getString(DETAIL_COLUMN_NAME))
                .price(rs.getFloat(PRICE_COLUMN_NAME))
                .stock(rs.getInt(STOCK_COLUMN_NAME))
                .build();
    }
}
