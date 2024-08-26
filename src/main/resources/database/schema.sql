CREATE TABLE `products` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(36),
  `name` varchar(255),
  `detail` varchar(255),
  `price` float,
  `stock` int,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `clients` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(36),
  `name` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `orders` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `code` VARCHAR(36),
  `user` bigint,
  `product` bigint,
  `amount` int,
  `status` VARCHAR(50),
  `created_at` timestamp,
  `updated_at` timestamp
);

ALTER TABLE `orders` ADD FOREIGN KEY (`user`) REFERENCES `clients` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`product`) REFERENCES `products` (`id`);
