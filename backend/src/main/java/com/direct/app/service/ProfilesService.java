package com.direct.app.service;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.shared.dto.ProfileDetailsDto;
import com.direct.app.shared.dto.ProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfilesService {

	public List<ProfileDto> retrieveSimilarUsers() throws Exception;
	public boolean editAccountDetails(ProfileDetailsDto detailsModel);
	public boolean editAccountImage(MultipartFile image) throws Exception;
	public UserEntity getAccountDetails() throws Exception;
}
