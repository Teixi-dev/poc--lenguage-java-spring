INSERT INTO `products` (`code`, `name`, `detail`, `stock`) VALUES
('a3e5b2e8-7e6b-11ec-90d6-0242ac120003', 'Laptop', 'Laptop con 16GB RAM y 512GB SSD', 15),
('a3e5b8e6-7e6b-11ec-90d6-0242ac120003', 'Smartphone', 'Smartphone con pantalla AMOLED', 30),
('a3e5bc1a-7e6b-11ec-90d6-0242ac120003', 'Monitor', 'Monitor 27 pulgadas 4K', 20);
INSERT INTO `clients` (`code`, `phone`, `name`) VALUES
('b4e5c42e-7e6b-11ec-90d6-0242ac120003', '555-1234', 'John Doe'),
('b4e5c9ec-7e6b-11ec-90d6-0242ac120003', '555-5678', 'Jane Smith');
INSERT INTO `orders` (`code`, `client`, `product`, `amount`, `status`) VALUES
('c5e6d34e-7e6b-11ec-90d6-0242ac120003', 'b4e5c42e-7e6b-11ec-90d6-0242ac120003', 'a3e5b2e8-7e6b-11ec-90d6-0242ac120003', 1, 'Pending'),
('c5e6d66c-7e6b-11ec-90d6-0242ac120003', 'b4e5c42e-7e6b-11ec-90d6-0242ac120003', 'a3e5b8e6-7e6b-11ec-90d6-0242ac120003', 2, 'Shipped'),
('c5e6d9b8-7e6b-11ec-90d6-0242ac120003', 'b4e5c9ec-7e6b-11ec-90d6-0242ac120003', 'a3e5bc1a-7e6b-11ec-90d6-0242ac120003', 1, 'Delivered');
