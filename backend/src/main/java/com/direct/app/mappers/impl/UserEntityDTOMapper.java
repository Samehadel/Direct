package com.direct.app.mappers.impl;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;

import java.util.ArrayList;
import java.util.List;

public class UserEntityDTOMapper implements EntityDTOMapper {

	@Override
	public BaseEntity mapDtoToEntity(BaseDTO dto) {
		return null;
	}

	@Override
	public List<? extends BaseEntity> mapDTOsToEntities(List<? extends BaseDTO> dtos) {
		return null;
	}

	// TODO: clean
	@Override
	public BaseDTO mapEntityToDTO(BaseEntity entity) {
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
	public List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities) {
		List<ProfileDto> profileDTOs = new ArrayList<>();

		entities.forEach(entity -> {
			ProfileDto profileDTO = (ProfileDto) mapEntityToDTO(entity);
			profileDTOs.add(profileDTO);
		});
		return null;
	}
}
