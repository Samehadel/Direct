package com.direct.app.repositery;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.direct.app.io.entities.PublicationEntity;

@Repository
public interface PublicationsRepository extends CrudRepository<PublicationEntity, Long> {

	public List<PublicationEntity> findByReceiverId(long id);

	@Modifying
	@Query(value = "UPDATE publications pub SET pub.is_read=true WHERE pub.id=:id", nativeQuery = true)
	public int markPublicationAsRead(long id);

	@Modifying
	@Query(value = "UPDATE publications pub SET pub.is_read=false WHERE pub.id=:id", nativeQuery = true)
	public int markPublicationAsUnRead(long id);

	@Query("SELECT publication FROM PublicationEntity publication " +
			"WHERE publication.sender.id = :senderId")
	public Set<PublicationEntity> findPublicationsBySenderId(Long senderId);
}
