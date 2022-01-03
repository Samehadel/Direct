package com.direct.app.repositery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.direct.app.io.entities.UserAuthorityEntity;

@Repository
public interface UserAuthorityRepository extends CrudRepository<UserAuthorityEntity, Long> {
}
