
-- Insert more data into categories
INSERT INTO categories (name) VALUES
('Toys'),
('Home Appliances'),
('Furniture');

-- Insert more data into products
INSERT INTO products (name, price, image_path, category_id) VALUES
('Action Figure', 24.99, './img/test.png', 4),
('Board Game', 34.99, './img/test.png', 4),
('Blender', 49.99, './img/test.png', 5),
('Microwave', 89.99, './img/test.png', 5),
('Chair', 59.99, './img/test.png', 6),
('Table', 129.99, './img/test.png', 6);

-- Insert more data into users
INSERT INTO users (username, password) VALUES
('dave', PASSWORD('pass123')),
('eve', PASSWORD('supersecure')),
('frank', PASSWORD('letmein')),
('grace', PASSWORD('welcome123')),
('heidi', PASSWORD('topsecret'));

-- Insert more data into purchases
INSERT INTO purchases (username, purchase_date) VALUES
('dave', '2025-01-09'),
('eve', '2025-01-08'),
('frank', '2025-01-07'),
('grace', '2025-01-06'),
('heidi', '2025-01-05');

-- Insert more data into purchased_products
INSERT INTO purchased_products (purchase_id, product_id, amount) VALUES
(4, 7, 1), -- Dave bought 1 Action Figure
(4, 10, 1), -- Dave bought 1 Chair
(5, 2, 1), -- Eve bought 1 Laptop
(5, 8, 2), -- Eve bought 2 Board Games
(6, 4, 1), -- Frank bought 1 Jeans
(6, 9, 1), -- Frank bought 1 Blender
(7, 11, 1), -- Grace bought 1 Table
(7, 6, 2), -- Grace bought 2 Non-Fiction Books
(8, 3, 4), -- Heidi bought 4 T-Shirts
(8, 5, 3); -- Heidi bought 3 Fiction Books
