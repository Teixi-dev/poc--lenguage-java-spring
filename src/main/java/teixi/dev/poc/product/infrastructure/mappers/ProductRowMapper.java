package teixi.dev.poc.product.infrastructure.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import teixi.dev.poc.product.domain.models.Product;
import teixi.dev.poc.product.domain.models.ProductCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class ProductRowMapper implements RowMapper<Product> {
    private static final String CODE_COLUMN = "code";
    private static final String NAME_COLUMN = "name";
    private static final String DETAIL_COLUMN = "detail";
    private static final String STOCK_COLUMN = "stock";

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .code(ProductCode.builder()
                        .value(UUID.fromString(rs.getString(CODE_COLUMN)))
                        .build()
                )
                .name(rs.getString(NAME_COLUMN))
                .detail(rs.getString(DETAIL_COLUMN))
                .stock(rs.getInt(STOCK_COLUMN))
                .build();
    }
}
