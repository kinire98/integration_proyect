
-- Insert even more data into categories
INSERT INTO categories (name) VALUES
('Kitchenware'),
('Sports'),
('Gardening'),
('Automotive'),
('Health & Beauty');

-- Insert even more data into products
INSERT INTO products (name, price, image_path, category_id) VALUES
('Knife Set', 79.99, './img/test.png', 7),
('Cooking Pan', 34.99, './img/test.png', 7),
('Soccer Ball', 24.99, './img/test.png', 8),
('Tennis Racket', 69.99, './img/test.png', 8),
('Garden Hose', 29.99, './img/test.png', 9),
('Lawn Mower', 199.99, './img/test.png', 9),
('Car Battery', 149.99, './img/test.png', 10),
('Air Freshener', 9.99, './img/test.png', 10),
('Skincare Kit', 49.99, './img/test.png', 11),
('Hair Dryer', 59.99, './img/test.png', 11);

-- Insert even more data into users
INSERT INTO users (username, password) VALUES
('ivan', PASSWORD('quickpass')),
('judy', PASSWORD('secure123')),
('karen', PASSWORD('adminpassword')),
('louis', PASSWORD('hunter2')),
('mike', PASSWORD('1234pass'));

-- Insert even more data into purchases
INSERT INTO purchases (username, purchase_date) VALUES
('ivan', '2025-01-04'),
('judy', '2025-01-03'),
('karen', '2025-01-02'),
('louis', '2025-01-01'),
('mike', '2024-12-31');

-- Insert even more data into purchased_products
INSERT INTO purchased_products (purchase_id, product_id, amount) VALUES
(9, 13, 1), -- Ivan bought 1 Knife Set
(9, 17, 1), -- Ivan bought 1 Garden Hose
(10, 14, 2), -- Judy bought 2 Cooking Pans
(10, 20, 1), -- Judy bought 1 Hair Dryer
(11, 15, 1), -- Karen bought 1 Soccer Ball
(11, 18, 1), -- Karen bought 1 Lawn Mower
(12, 16, 3), -- Louis bought 3 Tennis Rackets
(12, 19, 1), -- Louis bought 1 Car Battery
(13, 21, 2), -- Mike bought 2 Skincare Kits
(13, 7, 1);  -- Mike bought 1 Action Figure
