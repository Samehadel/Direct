package com.direct.app.service.implementation;

import com.direct.app.cache.CacheNames;
import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOConverterFacade;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.ProfilesService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.service.util.user_service_utils.ProfilesServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private EntityDTOConverterFacade converter;

	@Cacheable(cacheNames = CacheNames.SIMILAR_USERS, keyGenerator = "userBasedKeyGenerator")
	@Override
	public List<ProfileDto> retrieveSimilarUsers() throws Exception {
		UserEntity user = userService.getCurrentUserEntity_FullData();
		List<UserEntity> similarUsers = profilesServiceUtils
				.retrieveSimilarUsersBySubscriptions(user.getSubscriptions(), user.getId())
				.stream()
				.collect(Collectors.toList());

		List<ProfileDto> profileDTOs = (List<ProfileDto>) converter.mapToDTOs(similarUsers);

		return profileDTOs;
	}

	@Override
	public ProfileDto getProfileDetails() throws Exception {
		final UserEntity userEntity = userService.getCurrentUserEntity();
		return (ProfileDto) converter.mapToDTO(userEntity);
	}
}
