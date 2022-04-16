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
			"LEFT JOIN FETCH user.userDetails.userImage userImage " +
			"LEFT JOIN FETCH user.subscriptions subscriptions " +
			"LEFT JOIN FETCH user.sentRequests sentRequests " +
			"LEFT JOIN FETCH user.receivedRequests receivedRequests " +
			"LEFT JOIN FETCH user.sentConnections sentConnections " +
			"LEFT JOIN FETCH user.receivedConnections receivedConnections " +
			"LEFT JOIN FETCH user.sentPublications sentPublications " +
			"LEFT JOIN FETCH user.receivedPublications receivedPublications " +
			"WHERE user.username = :username")
	Optional<UserEntity> findByUsername_FullData(String username);

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findById(long id);

	@Query(value = "SELECT id from users u WHERE u.username=:username", nativeQuery = true)
	public long getUserId(String username);
}
