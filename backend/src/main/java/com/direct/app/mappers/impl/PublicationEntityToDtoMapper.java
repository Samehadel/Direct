package com.direct.app.mappers.impl;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.shared.dto.BaseDTO;
import com.direct.app.shared.dto.PublicationDto;

import java.util.ArrayList;
import java.util.List;

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
	public List<BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		List<PublicationDto> publications = new ArrayList<>();
		entities.forEach(entity -> {
			PublicationDto publicationDto = (PublicationDto) this.mapToDTO(entity);
			publications.add(publicationDto);
		});
		return null;
	}
}
