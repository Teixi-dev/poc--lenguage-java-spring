SELECT code, name, detail, stock
FROM products
WHERE code in (:productsCodes);