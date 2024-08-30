INSERT INTO products (code, name, detail, stock)
VALUES (:productCode, :productName, :productDetail, :productStock)
ON DUPLICATE KEY UPDATE
    stock = VALUES(stock),
    name = VALUES(name),
    detail = VALUES(detail);
