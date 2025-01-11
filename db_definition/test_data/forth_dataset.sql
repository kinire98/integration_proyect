
-- Insert additional categories
INSERT INTO categories (name) VALUES
('Electronics Accessories'),
('Footwear'),
('Beverages'),
('Snacks'),
('Office Supplies');

-- Insert additional products
INSERT INTO products (name, price, image_path, category_id) VALUES
('Headphones', 49.99, './img/test.png', 12),
('Wireless Mouse', 29.99, './img/test.png', 12),
('Running Shoes', 89.99, './img/test.png', 13),
('Sandals', 19.99, './img/test.png', 13),
('Coffee', 5.99, './img/test.png', 14),
('Energy Drink', 2.99, './img/test.png', 14),
('Potato Chips', 3.49, './img/test.png', 15),
('Chocolate Bar', 1.49, './img/test.png', 15),
('Notebook', 2.99, './img/test.png', 16),
('Ballpoint Pen', 0.99, './img/test.png', 16);

-- Insert additional users
INSERT INTO users (username, password) VALUES
('nancy', PASSWORD('mypassword123')),
('oscar', PASSWORD('oscarpass')),
('paul', PASSWORD('paul1234')),
('quincy', PASSWORD('qwerty')),
('rachel', PASSWORD('strongpass')),
('steve', PASSWORD('steve2024')),
('tracy', PASSWORD('tracysecure')),
('uma', PASSWORD('uma456')),
('victor', PASSWORD('victorpass')),
('wendy', PASSWORD('wendy123'));

-- Insert additional purchases
INSERT INTO purchases (username, purchase_date) VALUES
('nancy', '2024-12-30'),
('oscar', '2024-12-29'),
('paul', '2024-12-28'),
('quincy', '2024-12-27'),
('rachel', '2024-12-26'),
('steve', '2024-12-25'),
('tracy', '2024-12-24'),
('uma', '2024-12-23'),
('victor', '2024-12-22'),
('wendy', '2024-12-21');

-- Insert additional purchased products
INSERT INTO purchased_products (purchase_id, product_id, amount) VALUES
(14, 22, 2), -- Nancy bought 2 Headphones
(14, 26, 3), -- Nancy bought 3 Coffee
(15, 23, 1), -- Oscar bought 1 Wireless Mouse
(15, 29, 10), -- Oscar bought 10 Notebooks
(16, 24, 2), -- Paul bought 2 Running Shoes
(16, 28, 5), -- Paul bought 5 Potato Chips
(17, 25, 1), -- Quincy bought 1 Sandals
(17, 30, 20), -- Quincy bought 20 Ballpoint Pens
(18, 27, 6), -- Rachel bought 6 Energy Drinks
(18, 24, 1), -- Rachel bought 1 Running Shoes
(19, 21, 4), -- Steve bought 4 Skincare Kits
(19, 26, 8), -- Steve bought 8 Coffees
(20, 7, 1), -- Tracy bought 1 Knife Set
(20, 10, 1), -- Tracy bought 1 Chair
(21, 8, 2), -- Uma bought 2 Board Games
(21, 11, 1), -- Uma bought 1 Table
(22, 15, 3), -- Victor bought 3 Soccer Balls
(22, 28, 2), -- Victor bought 2 Potato Chips
(23, 18, 1), -- Wendy bought 1 Lawn Mower
(23, 30, 50); -- Wendy bought 50 Ballpoint Pens
