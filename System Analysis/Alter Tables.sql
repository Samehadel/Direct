ALTER TABLE user_details ADD FOREIGN KEY fk_user_details_user_id(user_id)
            REFERENCES users(id);
            
