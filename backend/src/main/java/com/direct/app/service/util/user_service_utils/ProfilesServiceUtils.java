package com.direct.app.service.util.user_service_utils;

import com.direct.app.io.entities.*;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.repositery.SubscriptionRepository;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.cache.annotation.CacheKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class ProfilesServiceUtils {

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private RequestRepository requestRepo;

	@Autowired
	private ConnectionRepository connectionRepo;

	@Autowired
	private UserService userService;


	public Set<UserEntity> retrieveSimilarUsersBySubscriptions(List<SubscriptionEntity> subscriptions, @CacheKey Long loggedUserId) throws Exception {

		// Step1: Extract similar subscriptions
		List<SubscriptionEntity> similarSubscriptions = retrieveSimilarSubscriptions(subscriptions, loggedUserId);

		Set<UserEntity> similarUsers = new HashSet<>();


		for (SubscriptionEntity sub : similarSubscriptions)
			similarUsers.add(sub.getUser());

		similarUsers = excludeConnectedProfiles(similarUsers, loggedUserId);
		similarUsers = excludeRequestedProfiles(similarUsers, loggedUserId);

		return similarUsers;
	}

	private List<SubscriptionEntity> retrieveSimilarSubscriptions(List<SubscriptionEntity> subscriptions, Long loggedUserId) {
		List<Integer> keywordsIds= getKeywordsIdsFromSubscriptions(subscriptions);

		if(!keywordsIds.isEmpty())
			return subscriptionRepo.findSimilarSubscriptions(keywordsIds, loggedUserId);

		return emptyList();
	}

	private List<Integer> getKeywordsIdsFromSubscriptions(List<SubscriptionEntity> subscriptions){
		return subscriptions
					.stream()
					.map(SubscriptionEntity::getKeyword)
					.map(KeywordEntity::getId)
					.collect(toList());
	}

	private Set<UserEntity> excludeConnectedProfiles(Set<UserEntity> similarUsers, Long loggedUserId) {

		List<ConnectionEntity> userConnections = connectionRepo.findByUserId(loggedUserId);
		List<UserEntity> excludedUsers = new ArrayList<UserEntity>();

		for (int i = 0; i < userConnections.size(); i++) {
			ConnectionEntity conn = userConnections.get(i);

			if (loggedUserIsFirstUser(conn))
				excludedUsers.add(conn.getSecondUser());
			else
				excludedUsers.add(conn.getFirstUser());
		}

		excludedUsers.forEach(similarUsers::remove);

		return similarUsers;
	}

	private boolean loggedUserIsFirstUser(ConnectionEntity connection){
		Long loggedUserId = userService.getCurrentUserId();
		Long firstUserId = connection.getFirstUser().getId();

		return firstUserId.equals(loggedUserId);
	}

	private Set<UserEntity> excludeRequestedProfiles(Set<UserEntity> similarUsers, Long loggedUserId) throws Exception {
		List<RequestEntity> userRequests = requestRepo.findRequestsBySenderOrReceiverId(loggedUserId);
		List<UserEntity> excludedUsers = new ArrayList<UserEntity>();

		for (RequestEntity req : userRequests)
			excludedUsers.add(getOtherUserInRequest(req));

		excludedUsers.forEach(similarUsers::remove);

		return similarUsers;
	}

	private UserEntity getOtherUserInRequest(RequestEntity request) throws Exception {
		if (loggedUserIsReceiver(request))
			return request.getSender();
		else
			return request.getReceiver();
	}

	private boolean loggedUserIsReceiver(RequestEntity request){
		Long loggedUserId = userService.getCurrentUserId();
		return request.getReceiver().getId().equals(loggedUserId);
	}
}