INSERT INTO category (id, description, name) VALUES
(50, 'Electronics and gadgets', 'Electronics'),
(100, 'Furniture and home decor', 'Furniture'),
(150, 'Books and stationery', 'Books'),
(200, 'Clothing and accessories', 'Clothing'),
(250, 'Sports and fitness equipment', 'Sports');



INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
(nextval('product_seq'), 'Smartphone with 128GB storage', 'Smartphone', 100, 699.99, 50),
(nextval('product_seq'), 'Wooden dining table for 6 people', 'Dining Table', 20, 299.99, 100),
(nextval('product_seq'), 'Fictional novel by a renowned author', 'Novel', 50, 14.99, 150),
(nextval('product_seq'), 'Winter jacket for men', 'Winter Jacket', 30, 89.99, 200),
(nextval('product_seq'), 'Yoga mat with non-slip surface', 'Yoga Mat', 200, 19.99, 250);
