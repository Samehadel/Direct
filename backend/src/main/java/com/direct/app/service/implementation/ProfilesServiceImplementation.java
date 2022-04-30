package com.direct.app.service.implementation;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.repositery.UserImageRepository;
import com.direct.app.service.ProfilesService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.service.util.user_service_utils.ProfilesServiceUtils;
import com.direct.app.shared.dto.ProfileDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

@Service
public class ProfilesServiceImplementation implements ProfilesService {

	@Autowired
	private SubscriptionService subscriptionsService;

	@Autowired
	private UserImageRepository userImageRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProfilesServiceUtils profilesServiceUtils;

	@Override
	public Set<UserEntity> retrieveSimilarUsers() throws Exception {
		UserEntity user = userService.getCurrentUserEntity_FullData();
		Set<UserEntity> similarUsers = profilesServiceUtils.retrieveSimilarUsersBySubscriptions(user.getSubscriptions(), user.getId());

		return similarUsers;
	}

	@Override
	public boolean editAccountDetails(ProfileDetailsDto detailsModel) {
		return false;
	}

	//TODO: remove boolean return
	//TODO: apply SRP
	@Override
	public boolean setAccountImage(MultipartFile image) throws Exception {
		final UserEntity user = userService.getCurrentUserEntity();

		UserImageEntity imageEntity = new UserImageEntity();

		//TODO: use ofNullable
		Optional<UserImageEntity> optionalImageEntity = userImageRepository.findByUserDetails(user.getUserDetails());

		if (optionalImageEntity.isPresent())
			imageEntity = optionalImageEntity.get();

		imageEntity.setImageData(image.getBytes());
		imageEntity.setImageFormat(image.getContentType());

		// Relationship exchange
		imageEntity.setUserDetails(user.getUserDetails());
		user.getUserDetails().setUserImage(imageEntity);

		userImageRepository.save(imageEntity);

		return imageEntity.getId() > 0 ? true : false;
	}

	@Override
	public UserEntity getAccountDetails() throws Exception {

		final long userId = userService.getCurrentUserId();
		return userService.retrieveUserById(userId);

	}

}
