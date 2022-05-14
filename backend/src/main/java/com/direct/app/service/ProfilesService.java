package com.direct.app.service;

import com.direct.app.shared.dto.ProfileDto;

import java.util.Set;

public interface ProfilesService {
	public Set<ProfileDto> retrieveSimilarUsers() throws Exception;
	public ProfileDto getProfileDetails() throws Exception;
}
