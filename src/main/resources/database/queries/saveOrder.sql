INSERT INTO orders (code, client, product, amount, status)
VALUES (:orderCode, :clientCode, :productCode, :amount, :status)
ON DUPLICATE KEY UPDATE
    status = VALUES(status);
