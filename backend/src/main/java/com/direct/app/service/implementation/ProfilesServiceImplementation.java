package com.direct.app.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.direct.app.service.IProfilesService;
import com.direct.app.service.util.user_service_utils.ProfilesServiceUtils;
import com.direct.app.ui.models.response.ProfileResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.service.ISubscriptionService;
import com.direct.app.service.IUserService;

@Service
public class ProfilesServiceImplementation implements IProfilesService {

	@Autowired
	ISubscriptionService subscriptionsService; 
	
	@Autowired 
	IUserService userService; 
	
	@Autowired
    ProfilesServiceUtils profilesServiceUtils;
	
	@Override
	public List<ProfileResponseModel> retrieveSimilarUsers() throws Exception {

		// Final users for return
		List<ProfileResponseModel> userModels = new ArrayList<>();

		// Extract username from security context
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		UserEntity user = userService.retrieveUser(username);
		
		Set<UserEntity> similarUsers = profilesServiceUtils.retrieveUsersBySubscriptions(user.getSubscriptions(), user.getId());
		
		for(UserEntity u: similarUsers) {
			ProfileResponseModel model = new ProfileResponseModel();
			
			//Copy related values to Dto
			BeanUtils.copyProperties(u, model);
			
			userModels.add(model);
		}
		return userModels;
	}

}
