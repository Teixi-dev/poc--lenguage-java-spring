CREATE TABLE `products` (
    `code` VARCHAR(36) PRIMARY KEY,
    `name` varchar(255),
    `detail` varchar(255),
    `stock` int,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `clients` (
    `code` VARCHAR(36) PRIMARY KEY,
    `phone` varchar(50),
    `name` varchar(255),
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `orders` (
    `code` VARCHAR(36) PRIMARY KEY,
    `client` VARCHAR(36),
    `product` VARCHAR(36),
    `amount` int,
    `status` VARCHAR(50),
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE
    `orders`
ADD
    FOREIGN KEY (`user`) REFERENCES `clients` (`code`);

ALTER TABLE
    `orders`
ADD
    FOREIGN KEY (`product`) REFERENCES `products` (`code`);