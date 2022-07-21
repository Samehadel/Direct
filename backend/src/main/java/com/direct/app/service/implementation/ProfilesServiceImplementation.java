package com.direct.app.service.implementation;

import com.direct.app.cache.CacheNames;
import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.ProfilesService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.service.util.user_service_utils.ProfilesServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.direct.app.enumerations.EntityDTOMapperType.USER_MAPPER;

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

	private EntityDTOMapper mapper;

	@Cacheable(cacheNames = CacheNames.SIMILAR_USERS, keyGenerator = "userBasedKeyGenerator")
	@Override
	public Set<ProfileDto> retrieveSimilarUsers() throws Exception {
		mapper = EntityDTOMapperFactory.getEntityDTOMapper(USER_MAPPER);
		Set<ProfileDto> profileDTOs = new HashSet<>();
		UserEntity user = userService.getCurrentUserEntity_FullData();
		Set<UserEntity> similarUsers = profilesServiceUtils.retrieveSimilarUsersBySubscriptions(user.getSubscriptions(), user.getId());


		similarUsers.forEach(similarUser -> {
			ProfileDto model = (ProfileDto) mapper.mapEntityToDTO(similarUser);
			profileDTOs.add(model);
		});

		return profileDTOs;
	}

	@Override
	public ProfileDto getProfileDetails() throws Exception {
		mapper = EntityDTOMapperFactory.getEntityDTOMapper(USER_MAPPER);;
		final UserEntity userEntity = userService.getCurrentUserEntity();
		return (ProfileDto) mapper.mapEntityToDTO(userEntity);
	}
}
