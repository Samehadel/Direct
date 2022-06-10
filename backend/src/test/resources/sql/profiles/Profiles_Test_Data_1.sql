INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2001, 'bio_user_1', 'major_field_user_1', '01234567890', 'professional_title_user_1');
INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2002, 'bio_user_2', 'major_field_user_2', '01234567891', 'professional_title_user_2');
INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2003, 'bio_user_3', 'major_field_user_3', '01234567892', 'professional_title_user_3');
INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2004, 'bio_user_4', 'major_field_user_4', '01234567893', 'professional_title_user_4');
INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2005, 'bio_user_5', 'major_field_user_2', '01234567891', 'professional_title_user_5');
INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2006, 'bio_user_6', 'major_field_user_3', '01234567892', 'professional_title_user_6');
INSERT INTO user_details (id, bio, major_field, phone, professional_title)
                    VALUES(2007, 'bio_user_7', 'major_field_user_4', '01234567893', 'professional_title_user_7');

INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1001, '$2a$10$.bFiSkEQ/XvVSc730N9Ma..8GIye169e4xiDMqyO4waihB8YZadGi', 'fName_1', 'lName_1', 'username_1', 2001);
INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1002, '$2a$10$42I4jZGwnEYJGkMLDu4t6u/DoP.cKQXLRIX7bgXN6Mz2bV3.TTg3C', 'fName_2', 'lName_2', 'username_2', 2002);
INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1003, '$2a$10$rHx7Ay5m9QE1.Raa2v16ROLhASbMc8WCSyk8kQy1BoVhJWEO0KETm', 'fName_3', 'lName_3', 'username_3', 2003);
INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1004, '$2a$10$GI5oyzIPmMK249QpZrABM.UwELVBPTeXH/lQjYcKYuc2BvpxfnqXm', 'fName_4', 'lName_4', 'username_4', 2004);
INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1005, '$2a$10$GI5oyzIPmMK249QpZrABM.UwELVBPTeXH/lQjYcKYuc2BvpxfnqXm', 'fName_5', 'lName_5', 'username_5', 2005);
INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1006, '$2a$10$GI5oyzIPmMK249QpZrABM.UwELVBPTeXH/lQjYcKYuc2BvpxfnqXm', 'fName_6', 'lName_6', 'username_6', 2006);
INSERT INTO users (id, encrypted_password, first_name, last_name, username, user_details_id)
            VALUES(1007, '$2a$10$GI5oyzIPmMK249QpZrABM.UwELVBPTeXH/lQjYcKYuc2BvpxfnqXm', 'fName_7', 'lName_7', 'username_7', 2007);

INSERT INTO user_authorities (id, role, user_id) VALUES (1, 'ROLE_USER', 1001);
INSERT INTO user_authorities (id, role, user_id) VALUES (2, 'ROLE_USER', 1002);
INSERT INTO user_authorities (id, role, user_id) VALUES (3, 'ROLE_USER', 1003);
INSERT INTO user_authorities (id, role, user_id) VALUES (4, 'ROLE_USER', 1004);
INSERT INTO user_authorities (id, role, user_id) VALUES (5, 'ROLE_USER', 1005);
INSERT INTO user_authorities (id, role, user_id) VALUES (6, 'ROLE_USER', 1006);
INSERT INTO user_authorities (id, role, user_id) VALUES (7, 'ROLE_USER', 1007);


INSERT INTO keywords VALUES (801, 'KEYWORD_DESCRIPTION_1');
INSERT INTO keywords VALUES (802, 'KEYWORD_DESCRIPTION_2');
INSERT INTO keywords VALUES (803, 'KEYWORD_DESCRIPTION_3');
INSERT INTO keywords VALUES (804, 'KEYWORD_DESCRIPTION_4');
INSERT INTO keywords VALUES (805, 'KEYWORD_DESCRIPTION_5');

INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (401, 801, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (402, 802, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (403, 805, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (404, 801, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (405, 802, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (406, 803, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (407, 803, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (408, 804, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (409, 805, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (411, 803, 1004);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (412, 804, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (413, 802, 1004);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (414, 805, 1004);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (415, 804, 1005);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (416, 801, 1005);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (417, 801, 1006);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (418, 802, 1006);



INSERT INTO requests (id, receiver_id, sender_id) VALUES (101, 1001, 1002);
INSERT INTO requests (id, receiver_id, sender_id) VALUES (102, 1001, 1007);
INSERT INTO requests (id, receiver_id, sender_id) VALUES (103, 1006, 1002);

INSERT INTO connections (id, first_user_id, second_user_id) VALUES (201, 1001, 1004);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (202, 1005, 1001);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (203, 1005, 1002);