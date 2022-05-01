package com.direct.app.repositery;

import com.direct.app.io.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	@Query("SELECT user FROM UserEntity user " +
			"LEFT JOIN FETCH user.authority authority " +
			"LEFT JOIN FETCH user.userDetails userDetails " +
			"LEFT JOIN FETCH user.subscriptions subscriptions " +
			"WHERE user.username = :username")
	Optional<UserEntity> findByUsername_FullData(String username);

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findById(long id);

	@Query(value = "SELECT id from users u WHERE u.username=:username", nativeQuery = true)
	public long getUserId(String username);
}
