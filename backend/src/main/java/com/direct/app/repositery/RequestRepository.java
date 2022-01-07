package com.direct.app.repositery;

import java.util.List;

import com.direct.app.io.entities.RequestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<RequestEntity, Long>{

	public RequestEntity findById(long id);
	
	@Query(value = "SELECT * FROM requests req WHERE req.sender_id =:userId", nativeQuery = true)
	List<RequestEntity> findSenderByUserId(long userId);

	@Query(value = "SELECT * FROM requests req WHERE req.receiver_id =:userId", nativeQuery = true)
	List<RequestEntity> findReceiverByUserId(long userId);

	@Query(value = "SELECT * FROM requests req WHERE req.sender_id =:userId OR req.receiver_id =:userId", nativeQuery = true)
	List<RequestEntity> findRequestByUserId(long userId);
}
