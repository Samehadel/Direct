package com.direct.app.service;

import com.direct.app.io.dto.KeywordDto;

import java.util.List;

public interface SubscriptionService {

	boolean createSubscription(Long userId, Integer keywordId) throws Exception;
	
	List<KeywordDto> getSubscriptions(Long userId);
	
	void dropSubscription(long subscriptionId);

	void dropSubscription(int keywordId) throws Exception;
}
