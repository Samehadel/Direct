package com.direct.app.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.direct.app.io.entities.ConnectionEntity;

import javax.transaction.Transactional;

public interface ConnectionRepository extends CrudRepository<ConnectionEntity, Long> {


	@Query( value = "SELECT * FROM connections conn WHERE conn.first_user_id =:userId",
			nativeQuery = true)
	List<ConnectionEntity> findSenderByUserId(long userId);

	@Query( value = "SELECT * FROM connections conn WHERE conn.second_user_id =:userId",
			nativeQuery = true)
	List<ConnectionEntity> findReceiverByUserId(long userId);

	@Query( value = "SELECT * FROM connections conn WHERE conn.first_user_id =:userId OR conn.second_user_id =:userId",
			nativeQuery = true)
	List<ConnectionEntity> findByUserId(long userId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM connections conn WHERE conn.id=:connectionId", nativeQuery = true)
	int removeConnection(long connectionId);
}
