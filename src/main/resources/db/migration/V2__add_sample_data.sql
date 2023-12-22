INSERT INTO handcrafted_schema.category (name, description)
VALUES
    ('Hair bands', 'Hair bands decorated with bows and other ornaments'),
    ('Hair hoops', 'Hair hoops decorated with flowers, animal ears and other ornaments'),
    ('Hairpins', 'Hairpins decorated with bows and other ornaments');

INSERT INTO handcrafted_schema.color (name)
VALUES
    ('Red'),
    ('Orange'),
    ('Yellow'),
    ('Green'),
    ('Blue'),
    ('Purple'),
    ('White'),
    ('Black'),
    ('Pink');

INSERT INTO handcrafted_schema.product (name, description, key_words, price, with_discount, in_stock, quantity, creation_date, category_id)
VALUES
    ('Bows on hairpins', 'Pink bows with a crystal center on hairpins', 'Pink, bows, crystal, hairpin, girls', 1.15, false, true, 6, current_timestamp, 3),
    ('Bows on hair bands', 'Red bows with a heart-shaped crystal center on hair bands', 'Red, bows, heart, crystal, hairband, girls', 1.75, false, true, 4, current_timestamp, 1),
    ('Butterfly on hairpin', 'Blue butterfly on a hairpin', 'Blue, butterfly, hairpins, girls', 2, false, true, 7, current_timestamp, 3),
    ('Butterfly on hair hoop', 'Blue-yellow butterfly on a hair hoop', 'Blue, yellow, butterfly, hairhoop, girls', 2.14, false, true, 2, current_timestamp, 2);

INSERT INTO handcrafted_schema.colors_in_product (color_id, product_id)
VALUES
    (9, 1),
    (1, 2),
    (5, 3),
    (5, 4),
    (3, 4);
