DELETE FROM handcrafted_schema.user_role
       WHERE user_id = 1;

INSERT INTO handcrafted_schema.user_role (user_id, roles)
VALUES
    (1, 'ADMIN');