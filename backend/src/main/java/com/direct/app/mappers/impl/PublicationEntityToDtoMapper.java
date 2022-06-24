package com.direct.app.mappers.impl;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.PublicationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PublicationEntityToDtoMapper implements EntityToDtoMapper {
	@Override
	public BaseDTO mapToDTO(BaseEntity entity) {
		PublicationDto publicationDto = new PublicationDto();
		PublicationEntity publicationEntity = (PublicationEntity) entity;
		UserEntity sender = publicationEntity.getSender();
		UserDetailsEntity senderDetails = sender.getUserDetails();

		publicationDto.setId(publicationEntity.getId());
		publicationDto.setSenderId(sender.getId());
		publicationDto.setContent(publicationEntity.getContent());
		publicationDto.setLink(publicationEntity.getLink());
		publicationDto.setIsRead(publicationEntity.isRead());

		publicationDto.getSenderDetails().setFirstName(sender.getFirstName());
		publicationDto.getSenderDetails().setLastName(sender.getLastName());
		publicationDto.getSenderDetails().setProfessionalTitle(senderDetails.getProfessionalTitle());

		return publicationDto;
	}

	@Override
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		return entities.stream()
						.map(this::mapToDTO)
						.collect(toList());
	}
}
