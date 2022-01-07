use find_me_job_db; 

Select * from users;
Select * from user_details;
Select * from users_images;
Select * from user_authorities;
Select * from subscriptions;
Select * from keywords;
Select * from requests;
Select * from publications;
Select * from connections;

select * from information_schema.table_constraints where CONSTRAINT_SCHEMA='find_me_job_db' and table_name="user_details";


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
