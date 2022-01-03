package com.direct.app.service;

import java.util.List;

import com.direct.app.ui.models.response.ProfileResponseModel;

public interface IProfilesService {

	public List<ProfileResponseModel> retrieveSimilarUsers() throws Exception;
}
