-- RESET TABLES
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM state;
DELETE FROM city;
DELETE FROM category;
DELETE FROM restaurant;
DELETE FROM payment_method;
DELETE FROM permission;
DELETE FROM profile;
DELETE FROM product;
DELETE FROM restaurant_payment_method;
DELETE FROM profile_permission;
DELETE FROM user;
DELETE FROM user_profile;

SET FOREIGN_KEY_CHECKS = 1;

-- RESET ID VALUES
ALTER TABLE state AUTO_INCREMENT = 1;
ALTER TABLE city AUTO_INCREMENT = 1;
ALTER TABLE category AUTO_INCREMENT = 1;
ALTER TABLE restaurant AUTO_INCREMENT = 1;
ALTER TABLE payment_method AUTO_INCREMENT = 1;
ALTER TABLE permission AUTO_INCREMENT = 1;
ALTER TABLE profile AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE restaurant_payment_method AUTO_INCREMENT = 1;
ALTER TABLE profile_permission AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;
ALTER TABLE user_profile AUTO_INCREMENT = 1;

-- POPULATE STATE TABLE
INSERT IGNORE INTO algafood.state (state.id, state.name) VALUES (1, 'São Paulo');
INSERT IGNORE INTO algafood.state (state.id, state.name) VALUES (2, 'Rio de Janeiro');

-- POPULATE CITY TABLE
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id) VALUES (1, 'São Paulo', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id) VALUES (2, 'Rio de Janeiro', 2);

-- POPULATE CATEGORY TABLE
INSERT IGNORE INTO algafood.category (category.id, category.type) VALUES (1, 'Australian');
INSERT IGNORE INTO algafood.category (category.id, category.type) VALUES (2, 'Burger');

-- POPULATE RESTAURANT TABLE
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id, restaurant.address_zip_code, restaurant.address, restaurant.address_number, restaurant.address_complement, restaurant.address_province, restaurant.address_city_id, restaurant.created, restaurant.updated) VALUES (1, 'Outback', 15.00, 1, '03112-090', 'Rua Marina Crespi', '274', 'Apartamento 12 A', 'Mooca - SP', 1, utc_timestamp, utc_timestamp);
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id, created, updated) VALUES (2, 'The Fifties', 8.00, 2, utc_timestamp, utc_timestamp);

-- POPULATE PAYMENT_METHOD TABLE
INSERT IGNORE INTO algafood.payment_method (payment_method.id, payment_method.method) VALUES (1, 'CREDIT CARD');
INSERT IGNORE INTO algafood.payment_method (payment_method.id, payment_method.method) VALUES (2, 'CASH');

-- POPULATE PERMISSION TABLE
INSERT IGNORE INTO algafood.permission (permission.id, permission.role) VALUES (1, 'CREATE');
INSERT IGNORE INTO algafood.permission (permission.id, permission.role) VALUES (2, 'UPDATE');

-- POPULATE RESTAURANT_PAYMENT_METHOD TABLE
INSERT IGNORE INTO algafood.restaurant_payment_method (restaurant_payment_method.restaurant_id, restaurant_payment_method.payment_method_id) VALUES (1, 1), (1, 2), (2, 1), (2, 2);

-- POPULATE PRODUCT TABLE
INSERT IGNORE INTO algafood.product (product.id, product.active, product.description, product.name, product.price, product.restaurant_id) VALUES (1, 1, 'Delicious bacon double burger', 'Double Bacon', 33.50, 2);
INSERT IGNORE INTO algafood.product (product.id, product.active, product.description, product.name, product.price, product.restaurant_id) VALUES (2, 1, 'Cheddar bacon burger', 'Ned Kelly', 40.00, 1);

-- POPULATE PROFILE TABLE
INSERT IGNORE INTO algafood.profile (profile.id, profile.name) VALUES (1, 'ADMIN');
INSERT IGNORE INTO algafood.profile (profile.id, profile.name) VALUES (2, 'QA');

-- POPULATE PROFILE_PERMISSION TABLE
INSERT IGNORE INTO algafood.profile_permission (profile_permission.profile_id, profile_permission.permission_id) VALUES (1, 1), (1, 2), (2, 1), (2, 2);

-- POPULATE USER TABLE
INSERT IGNORE INTO algafood.user (user.id, user.created, user.email, user.name, user.password) VALUES (1, utc_timestamp, 'gabriel@gmail.com', 'Gabriel Oliveira', '123');
INSERT IGNORE INTO algafood.user (user.id, user.created, user.email, user.name, user.password) VALUES (2, utc_timestamp, 'qa@gmail.com', 'QA Team', '123');

-- POPULATE USER_PROFILE TABLE
INSERT IGNORE INTO algafood.user_profile (user_profile.user_id, user_profile.profile_id) VALUES (1, 1), (1, 2), (2, 1), (2, 2);