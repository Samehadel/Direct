package com.direct.app.repositery;

import java.util.List;

import com.direct.app.io.entities.RequestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends CrudRepository<RequestEntity, Long>{

	@Query(value = 	"SELECT req FROM RequestEntity req " +
					"WHERE req.sender.id =:user_id")
	List<RequestEntity> findSenderByUserId(@Param("user_id") long userId);

	@Query(value = 	"SELECT req FROM RequestEntity req " +
					"WHERE req.receiver.id =:user_id")
	List<RequestEntity> findReceiverByUserId(@Param("user_id") Long userId);

	@Query(value = 	"SELECT req FROM RequestEntity req " +
					"WHERE req.sender.id =:user_id OR req.receiver.id =:user_id")
	List<RequestEntity> findRequestByUserId(@Param("user_id") Long userId);
}
