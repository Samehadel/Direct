use find_me_job_db; 

Select * from users;
Select * from user_details;
Select * from user_image;
Select * from user_authorities;
Select * from subscriptions;
Select * from keywords;
Select * from requests;
Select * from publications;
Select * from connections;

select * from information_schema.table_constraints where CONSTRAINT_SCHEMA='find_me_job_db';


insert into keywords (id, description) values (1, 'Java');
insert into keywords (id, description) values (2, 'Spring');
insert into keywords (id, description) values (3, 'Backend Developer');
insert into keywords (id, description) values (4, 'PHP');
insert into keywords (id, description) values (5, 'Laravel');
insert into keywords (id, description) values (6, 'React');
insert into keywords (id, description) values (7, 'Javascript');
insert into keywords (id, description) values (8, 'Frontend Developer');
insert into keywords (id, description) values (9, 'HTML');
insert into keywords (id, description) values (10, 'CSS');


# Update or Delete Here
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS=0;

# Update
update publications set receiver_id=reciever_id;
update requests set receiver_id=reciever_id;
update users u set username = user_name where u.id = 50;

# Delete
delete from users; 
delete from user_details;
delete from user_authorities;
delete from subscriptions;
delete from keywords;
delete from requests;
delete from publications;
delete from connections;

# Alter
ALTER TABLE requests DROP FOREIGN KEY fk_requests_user_id_reciever;

ALTER TABLE requests ADD FOREIGN KEY fk_requests_user_id_receiver(receiver_id)
            REFERENCES users(id);
ALTER TABLE user_image DROP COLUMN user_details_id;
#ALTER TABLE users DROP COLUMN user_name;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS=1;