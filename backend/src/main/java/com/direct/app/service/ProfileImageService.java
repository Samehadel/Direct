package com.direct.app.service;

import com.direct.app.shared.dto.ProfileImageDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
	public String setProfileImage(MultipartFile imageFile) throws Exception;
	public ProfileImageDTO getProfileImage() throws Exception;
}
