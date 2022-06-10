update users_sequence SET next_val = 1100;

delete from users WHERE id BETWEEN 1000 AND 1150 ;
--delete from users_sequence;

delete from connections WHERE (first_user_id BETWEEN 1000 AND 1150) OR (second_user_id BETWEEN 1000 AND 1150);
--delete from connections_sequence;

delete from keywords WHERE id BETWEEN 800 AND 850;
--delete from keywords_sequence;

delete from publications WHERE (receiver_id BETWEEN 1000 AND 1150) OR (sender_id BETWEEN 1000 AND 1150);
--delete from publications_sequence;

delete from requests WHERE (receiver_id BETWEEN 1000 AND 1150) OR (sender_id BETWEEN 1000 AND 1150);
--delete from requests_sequence;

delete from subscriptions WHERE user_id BETWEEN 1000 AND 1050;
--delete from subscriptions_sequence;

delete from user_authorities WHERE user_id BETWEEN 1000 AND 1150;
--delete from auth_sequence;

delete from user_details WHERE id BETWEEN 2000 AND 2150;
--delete from details_sequence;

delete from users_images WHERE id BETWEEN 5000 AND 5150;
--delete from images_sequence;
