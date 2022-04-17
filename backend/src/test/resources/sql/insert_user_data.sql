INSERT INTO users (id, encrypted_password, first_name, last_name, username)
            VALUES(1000, '$2a$10$.bFiSkEQ/XvVSc730N9Ma..8GIye169e4xiDMqyO4waihB8YZadGi', 'fName_1', 'lName_1', 'username_1');
INSERT INTO users (id, encrypted_password, first_name, last_name, username)
            VALUES(1001, '$2a$10$42I4jZGwnEYJGkMLDu4t6u/DoP.cKQXLRIX7bgXN6Mz2bV3.TTg3C', 'fName_2', 'lName_2', 'username_2');
INSERT INTO users (id, encrypted_password, first_name, last_name, username)
            VALUES(1002, '$2a$10$rHx7Ay5m9QE1.Raa2v16ROLhASbMc8WCSyk8kQy1BoVhJWEO0KETm', 'fName_3', 'lName_3', 'username_3');
INSERT INTO users (id, encrypted_password, first_name, last_name, username)
            VALUES(1003, '$2a$10$GI5oyzIPmMK249QpZrABM.UwELVBPTeXH/lQjYcKYuc2BvpxfnqXm', 'fName_4', 'lName_4', 'username_4');


INSERT INTO user_details (id, bio, major_field, phone, professional_title, user_id)
                    VALUES(2000, 'bio_user_1', 'major_field_user_1', '01234567890', 'professional_title_user_1', 1000);
INSERT INTO user_details (id, bio, major_field, phone, professional_title, user_id)
                    VALUES(2001, 'bio_user_2', 'major_field_user_2', '01234567891', 'professional_title_user_2', 1001);
INSERT INTO user_details (id, bio, major_field, phone, professional_title, user_id)
                    VALUES(2002, 'bio_user_3', 'major_field_user_3', '01234567892', 'professional_title_user_3', 1002);
INSERT INTO user_details (id, bio, major_field, phone, professional_title, user_id)
                    VALUES(2003, 'bio_user_4', 'major_field_user_4', '01234567893', 'professional_title_user_4', 1003);


INSERT INTO user_authorities (id, role, user_id) VALUES (1, 'ROLE_USER', 1000);
INSERT INTO user_authorities (id, role, user_id) VALUES (2, 'ROLE_USER', 1001);
INSERT INTO user_authorities (id, role, user_id) VALUES (3, 'ROLE_USER', 1002);
INSERT INTO user_authorities (id, role, user_id) VALUES (4, 'ROLE_USER', 1003);
