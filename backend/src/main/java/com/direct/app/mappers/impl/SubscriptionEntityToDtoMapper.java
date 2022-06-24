package com.direct.app.mappers.impl;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.mappers.EntityToDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SubscriptionEntityToDtoMapper implements EntityToDtoMapper {
	private EntityToDtoMapper entityToDtoMapper;

	public SubscriptionEntityToDtoMapper(){
		entityToDtoMapper = new KeywordEntityToDtoMapper();
	}

	@Override
	public BaseDTO mapToDTO(BaseEntity entity) {
		SubscriptionEntity subscriptionEntity = (SubscriptionEntity) entity;
		SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
		KeywordDto keywordDto = (KeywordDto) entityToDtoMapper.mapToDTO(subscriptionEntity.getKeyword());

		subscriptionDTO.setId(subscriptionEntity.getId());
		subscriptionDTO.setUserId(subscriptionEntity.getUser().getId());
		subscriptionDTO.setKeyword(keywordDto);

		return subscriptionDTO;
	}

	@Override
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		return entities
				.stream()
				.map(this::mapToDTO)
				.collect(toList());
	}
}
