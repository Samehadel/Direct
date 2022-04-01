package com.direct.app.service;

import java.util.List;

import com.direct.app.shared.dto.KeywordDto;

public interface SubscriptionService {

	public boolean createSubscription(Long userId, Integer keywordId) throws Exception;
	
	public List<KeywordDto> getSubscriptions(Long userId);
	
	public void dropSubscription(long subscriptionId);

	public void dropSubscription(int keywordId) throws Exception;
}
