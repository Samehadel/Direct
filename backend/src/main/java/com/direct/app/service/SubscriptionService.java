package com.direct.app.service;

import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {

	void subscribeToKeyword(Integer keywordId) throws Exception;
	
	List<SubscriptionDTO> getCurrentUserSubscriptions();

	void unsubscribeToKeyword(Integer keywordId) throws Exception;
}
