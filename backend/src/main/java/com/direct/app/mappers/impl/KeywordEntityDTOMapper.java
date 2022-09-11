package com.direct.app.mappers.impl;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.mappers.EntityDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

public class KeywordEntityDTOMapper implements EntityDTOMapper {

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
		KeywordEntity keywordEntity = (KeywordEntity) entity;
		KeywordDto keywordDto = new KeywordDto();

		keywordDto.setId(keywordEntity.getId());
		keywordDto.setDescription(keywordEntity.getDescription());

		return keywordDto;
	}

	@Override
	public List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities) {
		return entities.stream()
						.map(this::mapEntityToDTO)
						.collect(Collectors.toList());

	}
}
