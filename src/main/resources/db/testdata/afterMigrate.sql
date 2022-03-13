-- RESET TABLES
SET FOREIGN_KEY_CHECKS = 0;

DELETE
FROM state;
DELETE
FROM city;
DELETE
FROM category;
DELETE
FROM restaurant;
DELETE
FROM payment_method;
DELETE
FROM permission;
DELETE
FROM profile;
DELETE
FROM product;
DELETE
FROM `order`;
DELETE
FROM order_item;
DELETE
FROM restaurant_payment_method;
DELETE
FROM profile_permission;
DELETE
FROM user;
DELETE
FROM user_profile;
DELETE
FROM restaurant_responsible_user;

SET FOREIGN_KEY_CHECKS = 1;


-- RESET ID VALUES
ALTER TABLE state
    AUTO_INCREMENT = 1;
ALTER TABLE city
    AUTO_INCREMENT = 1;
ALTER TABLE category
    AUTO_INCREMENT = 1;
ALTER TABLE restaurant
    AUTO_INCREMENT = 1;
ALTER TABLE payment_method
    AUTO_INCREMENT = 1;
ALTER TABLE permission
    AUTO_INCREMENT = 1;
ALTER TABLE profile
    AUTO_INCREMENT = 1;
ALTER TABLE product
    AUTO_INCREMENT = 1;
ALTER TABLE `order`
    AUTO_INCREMENT = 1;
ALTER TABLE order_item
    AUTO_INCREMENT = 1;
ALTER TABLE restaurant_payment_method
    AUTO_INCREMENT = 1;
ALTER TABLE profile_permission
    AUTO_INCREMENT = 1;
ALTER TABLE user
    AUTO_INCREMENT = 1;
ALTER TABLE user_profile
    AUTO_INCREMENT = 1;
ALTER TABLE restaurant_responsible_user
    AUTO_INCREMENT = 1;


-- POPULATE STATE TABLE
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (1, 'São Paulo');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (2, 'Rio de Janeiro');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (3, 'Acre');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (4, 'Alagoas');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (5, 'Amapá');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (6, 'Amazonas');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (7, 'Bahia');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (8, 'Ceará');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (9, 'Distrito Federal');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (10, 'Espirito Santo');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (11, 'Goiás');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (12, 'Maranhão');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (13, 'Mato Grosso');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (14, 'Mato Grosso do Sul');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (15, 'Minas Gerais');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (16, 'Pará');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (17, 'Paraíba');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (18, 'Paraná');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (19, 'Pernambuco');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (20, 'Piauí');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (21, 'Rio Grande do Norte');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (22, 'Rio Grande do Sul');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (23, 'Rondônia');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (24, 'Roraima');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (25, 'Santa Catarina');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (26, 'Sergipe');
INSERT IGNORE INTO algafood.state (state.id, state.name)
VALUES (27, 'Tocantins');


-- POPULATE CITY TABLE
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (1, 'São Paulo', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (3, 'Guarulhos', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (2, 'Rio de Janeiro', 2);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (4, 'Campinas', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (5, 'Ribeirão Preto', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (6, 'São Caetano', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (7, 'Santos', 1);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (8, 'São Gonçalo', 2);
INSERT IGNORE INTO algafood.city (city.id, city.name, city.state_id)
VALUES (9, 'Niterói', 2);


-- POPULATE CATEGORY TABLE
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (1, 'Australian');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (2, 'Burger');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (3, 'Italian');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (4, 'Brazilian');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (5, 'French');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (6, 'Japanese');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (7, 'Chinese');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (8, 'Pizza');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (9, 'Sushi');
INSERT IGNORE INTO algafood.category (category.id, category.type)
VALUES (10, 'Steak');


-- POPULATE RESTAURANT TABLE
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id,
                                        restaurant.address_zip_code, restaurant.address, restaurant.address_number,
                                        restaurant.address_complement, restaurant.address_province,
                                        restaurant.address_city_id, restaurant.created, restaurant.updated)
VALUES (1, 'Outback', 15.00, 1, '03112-090', 'Rua Marina Crespi', '274', 'Apartamento 12 A', 'Mooca - SP', 1,
        utc_timestamp, utc_timestamp);
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id,
                                        restaurant.created, restaurant.updated)
VALUES (2, 'The Fifties', 8.00, 2, utc_timestamp, utc_timestamp);
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id,
                                        restaurant.created, restaurant.updated)
VALUES (3, 'Fogo de Chão', 25.00, 4, utc_timestamp, utc_timestamp);
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id,
                                        restaurant.created, restaurant.updated)
VALUES (4, 'Sushi One', 5.00, 9, utc_timestamp, utc_timestamp);
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id,
                                        restaurant.created, restaurant.updated)
VALUES (5, 'China in Box', 0.00, 7, utc_timestamp, utc_timestamp);
INSERT IGNORE INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.delivery_fee, restaurant.category_id,
                                        restaurant.created, restaurant.updated)
VALUES (6, 'McDonald\'s', 0.00, 7, utc_timestamp, utc_timestamp);


-- POPULATE PAYMENT_METHOD TABLE
INSERT IGNORE INTO algafood.payment_method (payment_method.id, payment_method.method)
VALUES (1, 'CREDIT CARD');
INSERT IGNORE INTO algafood.payment_method (payment_method.id, payment_method.method)
VALUES (2, 'CASH');
INSERT IGNORE INTO algafood.payment_method (payment_method.id, payment_method.method)
VALUES (3, 'DEBIT CARD');
INSERT IGNORE INTO algafood.payment_method (payment_method.id, payment_method.method)
VALUES (4, 'FOOD TICKET');


-- POPULATE PERMISSION TABLE
INSERT IGNORE INTO algafood.permission (permission.id, permission.role)
VALUES (1, 'CREATE');
INSERT IGNORE INTO algafood.permission (permission.id, permission.role)
VALUES (2, 'UPDATE');


-- POPULATE RESTAURANT_PAYMENT_METHOD TABLE
INSERT IGNORE INTO algafood.restaurant_payment_method (restaurant_payment_method.restaurant_id,
                                                       restaurant_payment_method.payment_method_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 4),
       (6, 1),
       (6, 2),
       (6, 3),
       (6, 4);


-- POPULATE PRODUCT TABLE
INSERT IGNORE INTO algafood.product (product.id, product.active, product.description, product.name, product.price,
                                     product.restaurant_id)
VALUES (1, 1, 'Bacon double burger', 'Double Bacon', 33.50, 2);
INSERT IGNORE INTO algafood.product (product.id, product.active, product.description, product.name, product.price,
                                     product.restaurant_id)
VALUES (2, 1, 'Cheddar bacon burger', 'Ned Kelly', 40.00, 1);
INSERT IGNORE INTO algafood.product (product.id, product.active, product.description, product.name, product.price,
                                     product.restaurant_id)
VALUES (3, 1, 'Coke Can', 'Coca-Cola', 8.90, 1);
INSERT IGNORE INTO algafood.product (product.id, product.active, product.description, product.name, product.price,
                                     product.restaurant_id)
VALUES (4, 1, 'Coke Can', 'Coca-Cola', 5.50, 2);


-- POPULATE PROFILE TABLE
INSERT IGNORE INTO algafood.profile (profile.id, profile.name)
VALUES (1, 'ADMIN');
INSERT IGNORE INTO algafood.profile (profile.id, profile.name)
VALUES (2, 'QA');


-- POPULATE PROFILE_PERMISSION TABLE
INSERT IGNORE INTO algafood.profile_permission (profile_permission.profile_id, profile_permission.permission_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2);


-- POPULATE USER TABLE
INSERT IGNORE INTO algafood.user (user.id, user.created, user.email, user.name, user.password)
VALUES (1, utc_timestamp, 'gabrielguarido.oliveira@gmail.com', 'Gabriel Oliveira', '$2a$12$SdfRgHK3B9UTlspTQlpEEebMMOgBOGYq4zXGT3L0V9ElaiHUjTiUi');
INSERT IGNORE INTO algafood.user (user.id, user.created, user.email, user.name, user.password)
VALUES (2, utc_timestamp, 'qa@gmail.com', 'QA Team', '$2a$12$SdfRgHK3B9UTlspTQlpEEebMMOgBOGYq4zXGT3L0V9ElaiHUjTiUi');


-- POPULATE USER_PROFILE TABLE
INSERT IGNORE INTO algafood.user_profile (user_profile.user_id, user_profile.profile_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2);


-- POPULATE RESTAURANT_RESPONSIBLE_USER TABLE
INSERT IGNORE INTO algafood.restaurant_responsible_user (restaurant_responsible_user.restaurant_id,
                                                         restaurant_responsible_user.user_id)
VALUES (1, 1),
       (2, 2);


-- POPULATE ORDER TABLE
INSERT IGNORE INTO algafood.`order` (`order`.id, `order`.external_key, `order`.subtotal, `order`.delivery_fee,
                                     `order`.total_price,
                                     `order`.restaurant_id, `order`.user_client_id,
                                     `order`.payment_method_id, `order`.address_city_id, `order`.address_zip_code,
                                     `order`.address, `order`.address_number,
                                     `order`.address_complement, `order`.address_province, `order`.status,
                                     `order`.created, `order`.confirmed,
                                     `order`.cancelled, `order`.delivered)
VALUES (1, '4e7c583e-4a45-11ec-85ee-fc34977b31ad', 48.90, 7.00, 55.90, 1, 1, 1, 1, '03112-090', 'Rua Marina Crespi',
        '274', 'Apartamento 12 A', 'Mooca - SP', 'CONFIRMED', utc_timestamp, utc_timestamp, null, null);
INSERT IGNORE INTO algafood.`order` (`order`.id, `order`.external_key, `order`.subtotal, `order`.delivery_fee,
                                     `order`.total_price,
                                     `order`.restaurant_id, `order`.user_client_id,
                                     `order`.payment_method_id, `order`.address_city_id, `order`.address_zip_code,
                                     `order`.address, `order`.address_number,
                                     `order`.address_complement, `order`.address_province, `order`.status,
                                     `order`.created, `order`.confirmed,
                                     `order`.cancelled, `order`.delivered)
VALUES (2, '450b0c9a-4a45-11ec-85ee-fc34977b31ad', 33.50, 0.00, 33.50, 2, 2, 2, 1, '03112-090', 'Rua Marina Crespi',
        '274', 'Apartamento 12 A', 'Mooca - SP', 'DELIVERED', utc_timestamp, utc_timestamp, null, utc_timestamp);


-- POPULATE ORDER_ITEM TABLE
INSERT IGNORE INTO algafood.order_item (order_item.id, order_item.amount, order_item.unit_price, order_item.total_price,
                                        order_item.observation, order_item.order_id, order_item.product_id)
VALUES (1, 1, 40.00, 40.00, 'Medium rare steak', 1, 2);
INSERT IGNORE INTO algafood.order_item (order_item.id, order_item.amount, order_item.unit_price, order_item.total_price,
                                        order_item.observation, order_item.order_id, order_item.product_id)
VALUES (2, 1, 8.90, 8.90, null, 1, 3);
INSERT IGNORE INTO algafood.order_item (order_item.id, order_item.amount, order_item.unit_price, order_item.total_price,
                                        order_item.observation, order_item.order_id, order_item.product_id)
VALUES (3, 1, 33.50, 33.50, 'Medium rare steak', 2, 1);