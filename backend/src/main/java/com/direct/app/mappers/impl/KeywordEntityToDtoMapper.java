package com.direct.app.mappers.impl;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.mappers.EntityToDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

public class KeywordEntityToDtoMapper implements EntityToDtoMapper {

	@Override
	public BaseDTO mapToDTO(BaseEntity entity) {
		KeywordEntity keywordEntity = (KeywordEntity) entity;
		KeywordDto keywordDto = new KeywordDto();

		keywordDto.setId(keywordEntity.getId());
		keywordDto.setDescription(keywordEntity.getDescription());

		return keywordDto;
	}

	@Override
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		return entities.stream()
						.map(this::mapToDTO)
						.collect(Collectors.toList());

	}
}
