package teixi.dev.poc.order.infrastructure.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import teixi.dev.poc.order.domain.models.Order;
import teixi.dev.poc.shared.domain.models.ClientCode;
import teixi.dev.poc.shared.domain.models.OrderCode;
import teixi.dev.poc.shared.domain.models.OrderStatus;
import teixi.dev.poc.shared.domain.models.ProductCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class OrderRowMapper implements RowMapper<Order> {
    private static final String CODE_COLUMN = "code";
    private static final String CLIENT_CODE_COLUMN = "client";
    private static final String PRODUCT_CODE_COLUMN = "product";
    private static final String AMOUNT_COLUMN = "amount";
    private static final String STATUS_COLUMN = "status";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .code(
                        OrderCode.builder()
                                .value(UUID.fromString(rs.getString(CODE_COLUMN)))
                                .build()
                )
                .clientCode(
                        ClientCode.builder()
                                .value(UUID.fromString(rs.getString(CLIENT_CODE_COLUMN)))
                                .build()
                )
                .productCode(
                        ProductCode.builder()
                                .value(UUID.fromString(rs.getString(PRODUCT_CODE_COLUMN)))
                                .build()
                )
                .amount(rs.getInt(AMOUNT_COLUMN))
                .status(OrderStatus.fromValue(rs.getString(STATUS_COLUMN)))
                .updatedAt(rs.getDate(CREATED_AT_COLUMN))
                .createdAt(rs.getDate(UPDATED_AT_COLUMN))
                .build();
    }
}
