INSERT INTO connections (id, first_user_id, second_user_id) VALUES (200, 1000, 1003);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (201, 1001, 1002);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (203, 1001, 1003);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (204, 1002, 1003);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (205, 1000, 1002);

INSERT INTO keywords VALUES (800, 'KEYWORD_DESCRIPTION_1');
INSERT INTO keywords VALUES (801, 'KEYWORD_DESCRIPTION_2');
INSERT INTO keywords VALUES (802, 'KEYWORD_DESCRIPTION_3');
INSERT INTO keywords VALUES (803, 'KEYWORD_DESCRIPTION_4');
INSERT INTO keywords VALUES (804, 'KEYWORD_DESCRIPTION_5');
INSERT INTO keywords VALUES (805, 'KEYWORD_DESCRIPTION_6');

INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (400, 800, 1000);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (401, 804, 1000);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (403, 801, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (404, 802, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (405, 805, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (406, 802, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (407, 803, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (408, 803, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (409, 804, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (410, 805, 1003);

