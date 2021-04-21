-- POPULATE CATEGORY TABLE
INSERT INTO algafood.category (category.id, category.type) VALUES (1, 'Australian');
INSERT INTO algafood.category (category.id, category.type) VALUES (2, 'Burger');

-- POPULATE RESTAURANT TABLE
INSERT INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.shipping_tax, restaurant.category_id) VALUES (1, 'Outback', 15.00, 1);
INSERT INTO algafood.restaurant (restaurant.id, restaurant.name, restaurant.shipping_tax, restaurant.category_id) VALUES (2, 'The Fifties', 8.00, 2);