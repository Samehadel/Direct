package com.direct.app.service.util.user_service_utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.repositery.SubscriptionRepository;

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

	public Set<UserEntity> retrieveSimilarUsersBySubscriptions(List<SubscriptionEntity> subscriptions, Long userId) throws Exception {

		// Step1: Extract similar subscriptions
		List<SubscriptionEntity> similarSubscriptions = retrieveSimilarSubscriptions(subscriptions, userId);

		// Store final users for return
		Set<UserEntity> users = new HashSet<>();

		// Step2: Extract users from similar subscriptions
		for (SubscriptionEntity sub : similarSubscriptions) {
			users.add(sub.getUser());
		}

		// Step3: Exclude users in connection list of the user
		users = excludeConnectedProfiles(users, userId);

		// Step4: Exclude users in requested list of the user
		users = excludeRequestedProfiles(users, userId);

		return users;
	}

	private List<SubscriptionEntity> retrieveSimilarSubscriptions(List<SubscriptionEntity> subscriptions, long userId) {
		List<Integer> keywords = new ArrayList<>();

		subscriptions.forEach(subscription -> {
			keywords.add(subscription.getKeyword().getId());
		});

		List<SubscriptionEntity> subs = subscriptionRepo.findSimilarSubscriptions(keywords, userId);

		return subs;

	}

	private Set<UserEntity> excludeConnectedProfiles(Set<UserEntity> users, long id) {

		List<ConnectionEntity> userConnections = connectionRepo.findByUserId(id);
		List<UserEntity> excludedUsers = new ArrayList<UserEntity>();

		for (int i = 0; i < userConnections.size(); i++) {
			ConnectionEntity conn = userConnections.get(i);

			if (conn.getFirstUser().getId() != id)
				excludedUsers.add(conn.getFirstUser());
			else
				excludedUsers.add(conn.getSecondUser());
		}

		for (int i = 0; i < excludedUsers.size(); i++)
			users.remove(excludedUsers.get(i));


		return users;
	}

	private Set<UserEntity> excludeRequestedProfiles(Set<UserEntity> users, Long userId) throws Exception {
		List<RequestEntity> userRequests = requestRepo.findRequestsBySenderOrReceiverId(userId);
		List<UserEntity> excludedUsers = new ArrayList<UserEntity>();

		for (RequestEntity req : userRequests)
			excludedUsers.add(getOtherUserInRequest(req));

		excludedUsers.forEach(excludedUser -> users.remove(excludedUser));

		return users;
	}

	private UserEntity getOtherUserInRequest(RequestEntity request) throws Exception {
		Long userId = userService.getCurrentUserId();

		if (request.getReceiver().getId() != userId)
			return request.getReceiver();
		else
			return request.getSender();
	}
}