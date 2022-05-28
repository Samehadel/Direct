package com.direct.app.mappers.impl;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.ProfileImageDTO;

import java.util.ArrayList;
import java.util.List;

public class UserImageEntityToDtoMapper implements EntityToDtoMapper {
	@Override
	public BaseDTO mapToDTO(BaseEntity entity) {
		ProfileImageDTO profileImageDTO = new ProfileImageDTO();
		UserImageEntity imageEntity = (UserImageEntity) entity;

		profileImageDTO.setId(imageEntity.getId());
		profileImageDTO.setImageName(imageEntity.getImageName());
		profileImageDTO.setImageFormat(imageEntity.getImageFormat());
		profileImageDTO.setImageUrl(imageEntity.getImageUrl());

		return profileImageDTO;
	}

	@Override
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		List<ProfileImageDTO> imageDTOs = new ArrayList<>();
		entities.forEach(entity -> {
			ProfileImageDTO imageDTO = (ProfileImageDTO) this.mapToDTO(entity);
			imageDTOs.add(imageDTO);
		});
		return imageDTOs;
	}
}
