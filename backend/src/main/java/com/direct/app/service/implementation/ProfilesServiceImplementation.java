package com.direct.app.service.implementation;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.mappers.impl.UserEntityToProfileDTOMapper;
import com.direct.app.repositery.UserImageRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.ProfilesService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.service.util.user_service_utils.ProfilesServiceUtils;
import com.direct.app.shared.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfilesServiceImplementation implements ProfilesService {

	@Autowired
	private SubscriptionService subscriptionsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProfilesServiceUtils profilesServiceUtils;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Set<ProfileDto> retrieveSimilarUsers() throws Exception {
		EntityToDtoMapper mapper = new UserEntityToProfileDTOMapper();
		Set<ProfileDto> profileDTOs = new HashSet<>();
		UserEntity user = userService.getCurrentUserEntity_FullData();
		Set<UserEntity> similarUsers = profilesServiceUtils.retrieveSimilarUsersBySubscriptions(user.getSubscriptions(), user.getId());

		similarUsers.forEach(similarUser -> {
			ProfileDto model = (ProfileDto) mapper.mapToDTO(similarUser);
			profileDTOs.add(model);
		});

		return profileDTOs;
	}

	@Override
	public void setAccountImage(String imageUrl) throws Exception {
		final UserEntity user = userService.getCurrentUserEntity();
		user.getUserDetails().setImageUrl(imageUrl);

		userRepository.save(user);
	}

	@Override
	public ProfileDto getAccountDetails() throws Exception {
		EntityToDtoMapper mapper = new UserEntityToProfileDTOMapper();
		final UserEntity userEntity = userService.getCurrentUserEntity();
		return (ProfileDto) mapper.mapToDTO(userEntity);
	}

}
