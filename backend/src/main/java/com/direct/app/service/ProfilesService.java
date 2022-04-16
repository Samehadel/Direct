package com.direct.app.service;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.shared.dto.ProfileDetailsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface ProfilesService {

	public Set<UserEntity> retrieveSimilarUsers() throws Exception;
	public boolean editAccountDetails(ProfileDetailsDto detailsModel);
	public boolean setAccountImage(MultipartFile image) throws Exception;
	public UserEntity getAccountDetails() throws Exception;
}
