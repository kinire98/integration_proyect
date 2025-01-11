
-- Insert even more categories
INSERT INTO categories (name) VALUES
('Pet Supplies'),
('Gaming'),
('Music'),
('Groceries'),
('Baby Products');

-- Insert even more products
INSERT INTO products (name, price, image_path, category_id) VALUES
('Dog Food', 19.99, './img/test.png', 17),
('Cat Toy', 9.99, './img/test.png', 17),
('Gaming Console', 499.99, './img/test.png', 18),
('Gaming Chair', 199.99, './img/test.png', 18),
('Electric Guitar', 799.99, './img/test.png', 19),
('Headphone Amplifier', 149.99, './img/test.png', 19),
('Rice', 1.99, './img/test.png', 20),
('Pasta', 0.99, './img/test.png', 20),
('Baby Stroller', 129.99, './img/test.png', 21),
('Baby Monitor', 89.99, './img/test.png', 21);

-- Insert even more users
INSERT INTO users (username, password) VALUES
('xander', PASSWORD('xander123')),
('yolanda', PASSWORD('yopass456')),
('zack', PASSWORD('zackpass')),
('aaron', PASSWORD('aaron2025')),
('bella', PASSWORD('bellasecure')),
('carla', PASSWORD('carlapass')),
('derek', PASSWORD('derek2024')),
('emma', PASSWORD('emmapass')),
('felix', PASSWORD('felixsecure')),
('gina', PASSWORD('ginapass'));

-- Insert even more purchases
INSERT INTO purchases (username, purchase_date) VALUES
('xander', '2024-12-20'),
('yolanda', '2024-12-19'),
('zack', '2024-12-18'),
('aaron', '2024-12-17'),
('bella', '2024-12-16'),
('carla', '2024-12-15'),
('derek', '2024-12-14'),
('emma', '2024-12-13'),
('felix', '2024-12-12'),
('gina', '2024-12-11');

-- Insert even more purchased products
INSERT INTO purchased_products (purchase_id, product_id, amount) VALUES
(24, 31, 3), -- Xander bought 3 Dog Foods
(24, 33, 1), -- Xander bought 1 Gaming Console
(25, 32, 2), -- Yolanda bought 2 Cat Toys
(25, 34, 1), -- Yolanda bought 1 Gaming Chair
(26, 35, 1), -- Zack bought 1 Electric Guitar
(26, 37, 10), -- Zack bought 10 Rice
(27, 36, 2), -- Aaron bought 2 Headphone Amplifiers
(27, 38, 15), -- Aaron bought 15 Pasta
(28, 39, 1), -- Bella bought 1 Baby Stroller
(28, 40, 1), -- Bella bought 1 Baby Monitor
(29, 24, 2), -- Carla bought 2 Running Shoes
(29, 8, 5),  -- Carla bought 5 Board Games
(30, 26, 6), -- Derek bought 6 Coffees
(30, 9, 3),  -- Derek bought 3 Chairs
(31, 21, 1), -- Emma bought 1 Skincare Kit
(31, 18, 1), -- Emma bought 1 Lawn Mower
(32, 23, 2), -- Felix bought 2 Wireless Mice
(32, 31, 3), -- Felix bought 3 Dog Foods
(33, 22, 4), -- Gina bought 4 Headphones
(33, 39, 1); -- Gina bought 1 Baby Stroller
