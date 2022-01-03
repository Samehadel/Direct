package com.direct.app.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.service.IKeywordService;
import com.direct.app.service.IUserService;
import com.direct.app.shared.dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.repositery.SubscriptionRepository;
import com.direct.app.service.ISubscriptionService;

@Service
public class SubscriptionServiceImplementation implements ISubscriptionService {

	@Autowired
    IUserService userService;
	
	@Autowired
    IKeywordService keywordService;
	
	@Autowired
	SubscriptionRepository subscriptionRepo; 
	
	
	@Override
	public boolean createSubscription(long userId, int keywordId) throws Exception {

		//Retrieve user
		UserEntity user = userService.retrieveUser(userId);
		
		//Retrieve keyword
		KeywordEntity keyword = keywordService.getKeywordById(keywordId);
		
		
		//create subscription
		SubscriptionEntity subscription = new SubscriptionEntity();
		
		/*Assign Relationships*/
		
		//Assign relationship between subscription and user (SUBSCRIPTION SIDE)
		subscription.setUser(user);
		
		//Assign relationship between subscription and keyword (SUBSCRIPTION SIDE)
		subscription.setKeyword(keyword);
		
		//Assign relationship between user and subscription (USER SIDE)
		user.addSubscription(subscription);
		
		//Assign relationship between keyword and subscription (KEYWORD SIDE)
		keyword.addSubscription(subscription);
		
		//Save subscription and check for nullness
		if(subscriptionRepo.save(subscription) != null) return true;
		
		return false;
	}

	@Override
	public List<SubscriptionDto> getSubscriptions(long userId) {

		List<SubscriptionDto> subscriptionsDto = new ArrayList<>();
				
		//Use repository
		List<SubscriptionEntity> subscriptions = subscriptionRepo.findAllByUserId(userId);
		
		for(SubscriptionEntity sub: subscriptions) {
			SubscriptionDto dto = new SubscriptionDto();
			
			//Copy data from entity to DTO
			dto.setId(sub.getId());
			dto.setKeywordId(sub.getKeyword().getId());
			dto.setKeywordDescription(sub.getKeyword().getDescription());
			
			subscriptionsDto.add(dto);
		}
		return subscriptionsDto;
	}

	@Override
	public void dropSubscription(long subscriptionId) {
		
		//Create new subscription to be deleted
		SubscriptionEntity subscription = new SubscriptionEntity();
		
		//Assign id to be dropped
		subscription.setId(subscriptionId);
		
		//Drop subscription
		subscriptionRepo.delete(subscription);
		
	}

	@Override
	public void dropSubscription(int keywordId) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		long userId = userService.retrieveUserId();

		subscriptionRepo.deleteSubscribedKeyword(userId, keywordId);
	}

}
