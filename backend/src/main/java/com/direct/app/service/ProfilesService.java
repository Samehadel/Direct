package com.direct.app.service;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.shared.dto.ProfileDetailsDto;
import com.direct.app.shared.dto.ProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface ProfilesService {
	public Set<ProfileDto> retrieveSimilarUsers() throws Exception;
	public void setAccountImage(String imageUrl) throws Exception;
	public ProfileDto getAccountDetails() throws Exception;
}
