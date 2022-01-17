package com.direct.app.service;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.ui.models.request.ProfileDetailsRequestModel;
import com.direct.app.ui.models.response.ProfileResponseModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProfilesService {

	public List<ProfileResponseModel> retrieveSimilarUsers() throws Exception;
	public boolean editAccountDetails(ProfileDetailsRequestModel detailsModel);
	public boolean editAccountImage(MultipartFile image) throws Exception;
	public UserEntity getAccountDetails() throws Exception;
}
