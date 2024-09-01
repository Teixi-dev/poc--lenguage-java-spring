INSERT INTO orders (code, client, product, amount, status, created_at, updated_at)
VALUES (:orderCode, :clientCode, :productCode, :amount, :status, :createdAt, :updatedAt)
ON DUPLICATE KEY UPDATE
    status = VALUES(status),
    amount = VALUES(amount),
    created_at = VALUES(created_at),
    updated_at = VALUES(updated_at);
