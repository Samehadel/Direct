package com.direct.app.mappers.impl;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.ProfileImageDTO;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.mappers.EntityDTOMapper;

import java.util.ArrayList;
import java.util.List;

public class UserImageEntityDTOMapper implements EntityDTOMapper {

	@Override
	public BaseEntity mapDtoToEntity(BaseDTO dto) {
		return null;
	}

	@Override
	public List<? extends BaseEntity> mapDTOsToEntities(List<? extends BaseDTO> dtos) {
		return null;
	}

	@Override
	public BaseDTO mapEntityToDTO(BaseEntity entity) {
		ProfileImageDTO profileImageDTO = new ProfileImageDTO();
		UserImageEntity imageEntity = (UserImageEntity) entity;

		profileImageDTO.setId(imageEntity.getId());
		profileImageDTO.setImageName(imageEntity.getImageName());
		profileImageDTO.setImageFormat(imageEntity.getImageFormat());
		profileImageDTO.setImageUrl(imageEntity.getImageUrl());

		return profileImageDTO;
	}

	@Override
	public List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities) {
		List<ProfileImageDTO> imageDTOs = new ArrayList<>();
		entities.forEach(entity -> {
			ProfileImageDTO imageDTO = (ProfileImageDTO) this.mapEntityToDTO(entity);
			imageDTOs.add(imageDTO);
		});
		return imageDTOs;
	}
}
