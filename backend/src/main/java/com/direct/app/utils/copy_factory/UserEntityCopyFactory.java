package com.direct.app.utils.copy_factory;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.shared.dto.ProfileDto;

public class UserEntityCopyFactory {

	public static ProfileDto copyProfileDTOFromEntity(UserEntity userEntity){
		ProfileDto profileDto = new ProfileDto();

		profileDto.setId(userEntity.getId());
		profileDto.setFirstName(userEntity.getFirstName());
		profileDto.setLastName(userEntity.getLastName());
		profileDto.setPhone(userEntity.getUserDetails().getPhone());
		profileDto.setMajorField(userEntity.getUserDetails().getMajorField());
		profileDto.setBio(userEntity.getUserDetails().getBio());
		profileDto.setProfessionalTitle(userEntity.getUserDetails().getProfessionalTitle());
		profileDto.setImageData(userEntity.getUserDetails().getUserImage().getImageData());
		profileDto.setImageFormat(userEntity.getUserDetails().getUserImage().getImageFormat());

		return profileDto;
	}
}
