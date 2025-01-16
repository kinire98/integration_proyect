USE tpv_test;
CREATE TABLE categories(
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  PRIMARY KEY(id)
);
CREATE TABLE products(
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30),
  price DECIMAL(10, 2),
  image_path VARCHAR(100),
  last_modification DATE,
  category_id BIGINT,
  PRIMARY KEY(ID),
  FOREIGN KEY(category_id) REFERENCES categories(id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE TABLE users (
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  PRIMARY KEY(username)
);
CREATE TABLE purchases (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255),
  purchase_date DATE,
  PRIMARY KEY(id),
  FOREIGN KEY(username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE TABLE purchased_products (
  purchase_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  amount INT,
  PRIMARY KEY(purchase_id, product_id),
  FOREIGN KEY(purchase_id) REFERENCES purchases(id) ON UPDATE CASCADE ON DELETE SET NULL,
  FOREIGN KEY(product_id) REFERENCES products(id)
);
