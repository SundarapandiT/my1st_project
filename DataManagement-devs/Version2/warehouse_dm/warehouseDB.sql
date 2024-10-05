-- Step 1: Create the warehouseDB database
CREATE DATABASE warehouseDB;

-- Step 2: Select the warehouseDB database to work with
USE warehouseDB;

-- Step 3: Create the products table
CREATE TABLE products (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock_count INT NOT NULL
);

-- Step 4: Insert product data into the products table
INSERT INTO products (id, name, stock_count) VALUES
('Product111', 'Refrigerator', 60),
('Product123', 'Laptop', 150),
('Product345', 'Mobiles', 100),
('Product567', 'Televisions', 80),
('Product789', 'AC', 70);
