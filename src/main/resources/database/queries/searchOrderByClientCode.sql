SELECT code, client, product, amount, status, created_at, updated_at
FROM orders
WHERE client = :clientCode;