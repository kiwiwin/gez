CREATE TABLE Products (
  id SERIAL PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE Prices (
  id SERIAL PRIMARY KEY,
  price INT NOT NULL,
  modified_at timestamp,
  modified_by VARCHAR(32),
  product_id INT NOT NULL,
  Foreign Key (product_id) REFERENCES Products(id)
);