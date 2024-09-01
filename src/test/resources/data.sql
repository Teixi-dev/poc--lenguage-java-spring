INSERT INTO `products` (`code`, `name`, `detail`, `stock`) VALUES
('a3e5b2e8-7e6b-11ec-90d6-0242ac120003', 'Laptop', 'Laptop con 16GB RAM y 512GB SSD', 15),
('a3e5b8e6-7e6b-11ec-90d6-0242ac120003', 'Smartphone', 'Smartphone con pantalla AMOLED', 30),
('a3e5bc1a-7e6b-11ec-90d6-0242ac120003', 'Monitor', 'Monitor 27 pulgadas 4K', 20);
INSERT INTO `clients` (`code`, `phone`, `name`, `created_at`, `updated_at`) VALUES
('b4e5c42e-7e6b-11ec-90d6-0242ac120003', '555-1234', 'John Doe', '2014-05-21 13:30:10', '2014-05-28 15:30:10'),
('538e2695-8b66-4bca-8b57-93d18f746be4', '555-5678', 'Jane Doe', '2014-05-21 13:30:10', '2014-05-28 15:30:10'),
('b4e5c9ec-7e6b-11ec-90d6-0242ac120003', '555-5678', 'Jane Smith', '2014-05-21 13:30:10', '2014-05-28 15:30:10');
INSERT INTO `orders` (`code`, `client`, `product`, `amount`, `status`, `created_at`, `updated_at`) VALUES
('c5e6d34e-7e6b-11ec-90d6-0242ac120003', 'b4e5c42e-7e6b-11ec-90d6-0242ac120003', 'a3e5b2e8-7e6b-11ec-90d6-0242ac120003', 1, 'Pending', '2014-05-21 13:30:10', '2014-05-28 15:30:10'),
('c5e6d66c-7e6b-11ec-90d6-0242ac120003', 'b4e5c42e-7e6b-11ec-90d6-0242ac120003', 'a3e5b8e6-7e6b-11ec-90d6-0242ac120003', 2, 'Shipped', '2014-05-21 10:30:10', '2014-05-24 10:30:10'),
('c5e6d9b8-7e6b-11ec-90d6-0242ac120003', 'b4e5c9ec-7e6b-11ec-90d6-0242ac120003', 'a3e5bc1a-7e6b-11ec-90d6-0242ac120003', 1, 'Delivered', '2014-05-23 11:30:10', '2014-05-24 12:30:10'),
('9741e2a5-53ab-4cc2-8445-dc241bea5a6d', 'b4e5c9ec-7e6b-11ec-90d6-0242ac120003', 'a3e5bc1a-7e6b-11ec-90d6-0242ac120003', 1, 'Invalid Status', '2014-05-23 11:30:10', '2014-05-24 12:30:10');
