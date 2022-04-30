INSERT INTO requests (id, receiver_id, sender_id) VALUES (100, 1000, 1001);
INSERT INTO requests (id, receiver_id, sender_id) VALUES (101, 1000, 1002);

INSERT INTO connections (id, first_user_id, second_user_id) VALUES (200, 1000, 1003);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (201, 1001, 1002);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (203, 1001, 1003);
INSERT INTO connections (id, first_user_id, second_user_id) VALUES (204, 1002, 1003);

INSERT INTO keywords VALUES (800, 'KEYWORD_DESCRIPTION_1');
INSERT INTO keywords VALUES (801, 'KEYWORD_DESCRIPTION_2');
INSERT INTO keywords VALUES (802, 'KEYWORD_DESCRIPTION_3');
INSERT INTO keywords VALUES (803, 'KEYWORD_DESCRIPTION_4');
INSERT INTO keywords VALUES (804, 'KEYWORD_DESCRIPTION_5');
INSERT INTO keywords VALUES (805, 'KEYWORD_DESCRIPTION_6');

INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (400, 800, 1000);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (401, 804, 1000);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (402, 805, 1000);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (403, 801, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (404, 802, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (405, 805, 1001);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (406, 800, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (407, 802, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (408, 803, 1002);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (409, 803, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (410, 804, 1003);
INSERT INTO subscriptions (id, keyword_id, user_id) VALUES (411, 805, 1003);

INSERT INTO publications (id, content, is_read, link, receiver_id, sender_id)
                  VALUES (900, 'CONTENT_1', true, 'JOB_LINK_1', 1000, 1003);
INSERT INTO publications (id, content, is_read, link, receiver_id, sender_id)
                  VALUES (901, 'CONTENT_1', false, 'JOB_LINK_1', 1001, 1003);
INSERT INTO publications (id, content, is_read, link, receiver_id, sender_id)
                  VALUES (902, 'CONTENT_2', false, 'JOB_LINK_2', 1002, 1001);
INSERT INTO publications (id, content, is_read, link, receiver_id, sender_id)
                  VALUES (903, 'CONTENT_3', false, 'JOB_LINK_3', 1003, 1000);
INSERT INTO publications (id, content, is_read, link, receiver_id, sender_id)
                  VALUES (904, 'CONTENT_4', true, 'JOB_LINK_4', 1000, 1003);
INSERT INTO publications (id, content, is_read, link, receiver_id, sender_id)
                  VALUES (905, 'CONTENT_4', true, 'JOB_LINK_4', 1000, 1003);
