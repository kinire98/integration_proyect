
-- Insert test data into categories
INSERT INTO categories (name) VALUES
('Electronics'),
('Clothing'),
('Books');

-- Insert test data into products
INSERT INTO products (name, price, image_path, category_id) VALUES
('Smartphone', 699.99, './img/test.png', 1),
('Laptop', 1199.99, './img/test.png', 1),
('T-shirt', 19.99, './img/test.png', 2),
('Jeans', 39.99, './img/test.png', 2),
('Fiction Book', 14.99, './img/test.png', 3),
('Non-Fiction Book', 24.99, './img/test.png', 3);

-- Insert test data into users
INSERT INTO users (username, password) VALUES
('alice', PASSWORD('password123')),
('bob', PASSWORD('securepass')),
('charlie', PASSWORD('mypassword'));

-- Insert test data into purchases
INSERT INTO purchases (username, purchase_date) VALUES
('alice', '2025-01-10'),
('bob', '2025-01-09'),
('charlie', '2025-01-08');

-- Insert test data into purchased_products
INSERT INTO purchased_products (purchase_id, product_id, amount) VALUES
(1, 1, 1), -- Alice bought 1 Smartphone
(1, 5, 2), -- Alice bought 2 Fiction Books
(2, 3, 3), -- Bob bought 3 T-shirts
(2, 6, 1), -- Bob bought 1 Non-Fiction Book
(3, 2, 1), -- Charlie bought 1 Laptop
(3, 4, 2); -- Charlie bought 2 Jeans
