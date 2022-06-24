package com.direct.app.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.direct.app.io.entities.SubscriptionEntity;

import javax.transaction.Transactional;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
	
	List<SubscriptionEntity> findAllByUserId(long id);
	
	@Query(value = "SELECT * FROM subscriptions subs WHERE subs.keyword_id in :keywords AND subs.user_id != :userId", nativeQuery = true)
	List<SubscriptionEntity> findSimilarSubscriptions(List<Integer> keywords, Long userId);

	@Query(value = "DELETE FROM subscriptions subs WHERE subs.user_id=:userId and keyword_id=:keywordId", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteSubscribedKeyword(@Param(value = "userId") Long userId, @Param(value = "keywordId") Integer keywordId);
}
