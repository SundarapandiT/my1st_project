CREATE DATABASE warehouse;

USE warehouse;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    quantity INT
);

-- Insert sample data
INSERT INTO products (product_id, name, quantity)
VALUES ('12345', 'Product A', 100),
       ('54321', 'Product B', 50);
