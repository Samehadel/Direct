package com.direct.app.service.implementation;

import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.repositery.UserImageRepository;
import com.direct.app.service.IProfilesService;
import com.direct.app.service.ISubscriptionService;
import com.direct.app.service.IUserService;
import com.direct.app.service.util.user_service_utils.ProfilesServiceUtils;
import com.direct.app.ui.models.request.ProfileDetailsRequestModel;
import com.direct.app.ui.models.response.ProfileResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfilesServiceImplementation implements IProfilesService {

	@Autowired
	private ISubscriptionService subscriptionsService;

	@Autowired
	private UserImageRepository userImageRepository;

	@Autowired 
	private IUserService userService;
	
	@Autowired
    private ProfilesServiceUtils profilesServiceUtils;


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
			BeanUtils.copyProperties(u.getUserDetails(), model,"id");
			BeanUtils.copyProperties(u.getUserDetails().getUserImage(), model, "id");

			userModels.add(model);
		}
		return userModels;
	}

	@Override
	public boolean editAccountDetails(ProfileDetailsRequestModel detailsModel) {


		return false;
	}

	@Override
	public boolean editAccountImage(MultipartFile image) throws Exception {

		final long userId = userService.retrieveUserId();
		final UserEntity user = userService.retrieveUser(userId);

		UserImageEntity imageEntity = new UserImageEntity();

		Optional<UserImageEntity> optionalImageEntity = userImageRepository.findByUserDetails(user.getUserDetails());

		if(optionalImageEntity.isPresent())
			imageEntity = optionalImageEntity.get();

		imageEntity.setImageData(image.getBytes());
		imageEntity.setImageFormat(image.getContentType());

		// Relationship exchange
		imageEntity.setUserDetails(user.getUserDetails());
		user.getUserDetails().setUserImage(imageEntity);

		// Save the image
		userImageRepository.save(imageEntity);

		return imageEntity.getId() > 0 ? true : false;
	}

	@Override
	public UserEntity getAccountDetails() throws Exception {

		final long userId = userService.retrieveUserId();
		return userService.retrieveUser(userId);

	}

}
