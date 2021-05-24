-- POPULATE STATE TABLE
INSERT INTO algafood.state (state.id, state.name) VALUES (1, 'São Paulo');
INSERT INTO algafood.state (state.id, state.name) VALUES (2, 'Rio de Janeiro');

-- POPULATE CITY TABLE
INSERT INTO algafood.city (city.id, city.name, city.state_id) VALUES (1, 'São Paulo', 1);
INSERT INTO algafood.city (city.id, city.name, city.state_id) VALUES (2, 'Rio de Janeiro', 2);

-- POPULATE CATEGORY TABLE
INSERT INTO algafood.category (category.id, category.type) VALUES (1, 'Australian');
INSERT INTO algafood.category (category.id, category.type) VALUES (2, 'Burger');

-- POPULATE RESTAURANT TABLE
INSERT INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.shipping_tax, restaurant.category_id, restaurant.address_zip_code, restaurant.address, restaurant.address_number, restaurant.address_complement, restaurant.address_province, restaurant.address_city_id) VALUES (1, 'Outback', 15.00, 1, '03112-090', 'Rua Marina Crespi', '274', 'Apartamento 12 A', 'Mooca - SP', 1);
INSERT INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.shipping_tax, restaurant.category_id) VALUES (2, 'The Fifties', 8.00, 2);

-- POPULATE PAYMENT_METHOD TABLE
INSERT INTO algafood.payment_method (payment_method.id, payment_method.method) VALUES (1, 'CREDIT CARD');
INSERT INTO algafood.payment_method (payment_method.id, payment_method.method) VALUES (2, 'CASH');

-- POPULATE PERMISSION TABLE
INSERT INTO algafood.permission (permission.id, permission.role) VALUES (1, 'CREATE');
INSERT INTO algafood.permission (permission.id, permission.role) VALUES (2, 'UPDATE');

-- POPULATE RESTAURANT_PAYMENT_METHOD TABLE
INSERT INTO algafood.restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1), (1, 2), (2, 1), (2, 2);