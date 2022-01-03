package com.direct.app.repositery;

import com.direct.app.io.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUserName(String userName);

	UserEntity findById(long id);

	@Query(value = "SELECT id from users u WHERE u.username=:username", nativeQuery = true)
	public long getUserId(String username);
}
