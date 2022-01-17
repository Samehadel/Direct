package com.direct.app.repositery;

import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserImageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserImageRepository extends CrudRepository<UserImageEntity, Long> {

    //@Query(name = "SELECT * FROM users_images WHERE user_details_id=:userDetails", nativeQuery = true)
    public Optional<UserImageEntity> findByUserDetails(UserDetailsEntity userDetails);
}
