package com.direct.app.service;

import com.direct.app.io.dto.ProfileDto;

import java.util.List;

public interface ProfilesService {
	public List<ProfileDto> retrieveSimilarUsers() throws Exception;
	public ProfileDto getProfileDetails() throws Exception;
}
