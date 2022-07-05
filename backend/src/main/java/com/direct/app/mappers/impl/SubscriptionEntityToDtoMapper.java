package com.direct.app.mappers.impl;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.mappers.EntityDTOMapper;

import java.util.List;

import static com.direct.app.enumerations.EntityDTOMapperType.KEYWORD_MAPPER;
import static java.util.stream.Collectors.toList;

public class SubscriptionEntityToDtoMapper implements EntityDTOMapper {
	private EntityDTOMapper entityDTOMapper;

	public SubscriptionEntityToDtoMapper(){
		entityDTOMapper = EntityDTOMapperFactory.getEntityDTOMapper(KEYWORD_MAPPER);
	}

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
		SubscriptionEntity subscriptionEntity = (SubscriptionEntity) entity;
		SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
		KeywordDto keywordDto = (KeywordDto) entityDTOMapper.mapEntityToDTO(subscriptionEntity.getKeyword());

		subscriptionDTO.setId(subscriptionEntity.getId());
		subscriptionDTO.setUserId(subscriptionEntity.getUser().getId());
		subscriptionDTO.setKeyword(keywordDto);

		return subscriptionDTO;
	}

	@Override
	public List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities) {
		return entities
				.stream()
				.map(this::mapEntityToDTO)
				.collect(toList());
	}
}
