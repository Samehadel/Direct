package com.company.app.repositery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.app.io.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUserName(String userName);

	UserEntity findById(long id);

	@Query(value = "SELECT id from users u WHERE u.username=:username", nativeQuery = true)
	public long getUserId(String username);
}
