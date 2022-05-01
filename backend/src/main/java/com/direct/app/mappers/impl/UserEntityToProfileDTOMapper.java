package com.direct.app.mappers.impl;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.shared.dto.BaseDTO;
import com.direct.app.shared.dto.ProfileDto;

import java.util.List;

public class UserEntityToProfileDTOMapper implements EntityToDtoMapper {

	// TODO: clean
	@Override
	public BaseDTO mapToDTO(BaseEntity entity) {
		UserEntity userEntity = (UserEntity) entity;
		ProfileDto profileDto = new ProfileDto();

		profileDto.setId(userEntity.getId());
		profileDto.setFirstName(userEntity.getFirstName());
		profileDto.setLastName(userEntity.getLastName());
		profileDto.setPhone(userEntity.getUserDetails().getPhone());
		profileDto.setMajorField(userEntity.getUserDetails().getMajorField());
		profileDto.setBio(userEntity.getUserDetails().getBio());
		profileDto.setProfessionalTitle(userEntity.getUserDetails().getProfessionalTitle());

		return profileDto;
	}

	@Override
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		return null;
	}
}
