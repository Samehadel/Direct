package com.direct.app.mappers.impl;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.DtoToEntityMapper;
import com.direct.app.io.dto.SenderDetails;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.PublicationDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

public class PublicationDtoToEntityMapper implements DtoToEntityMapper {
	private UserDetailsEntity senderDetailsEntity;
	private UserEntity sender;
	private PublicationDto publicationDto;
	private PublicationEntity publicationEntity;

	@Override
	public BaseEntity mapToEntity(BaseDTO dto) {
		publicationDto = (PublicationDto) dto;
		publicationEntity = new PublicationEntity();

		setSenderEntity();
		setSenderDetailsEntity();
		setSenderInfo();
		setPublicationContent();

		return publicationEntity;
	}

	private void setSenderEntity() {
		Long senderId = publicationDto.getSenderId();
		sender = ofNullable(publicationEntity.getSender())
				.orElse(new UserEntity());
		publicationEntity.setSender(sender);
		sender.setId(senderId);
	}

	private void setSenderDetailsEntity() {
		SenderDetails senderDetails =
				ofNullable(publicationDto.getSenderDetails())
						.orElse(new SenderDetails());
		senderDetailsEntity =
				ofNullable(sender.getUserDetails())
						.orElse(new UserDetailsEntity());
		senderDetailsEntity.setProfessionalTitle(senderDetails.getProfessionalTitle());
	}

	private void setSenderInfo() {
		SenderDetails senderDetails = publicationDto.getSenderDetails();
		sender.setUserDetails(senderDetailsEntity);
		sender.setFirstName(senderDetails.getFirstName());
		sender.setLastName(senderDetails.getLastName());
	}

	private void setPublicationContent() {
		publicationEntity.setId(publicationDto.getId());
		publicationEntity.setContent(publicationDto.getContent());
		publicationEntity.setLink(publicationDto.getLink());
		publicationEntity.setRead(publicationDto.getIsRead());
	}

	@Override
	public List<? extends BaseEntity> mapToEntities(List<? extends BaseDTO> dtos) {
		List<PublicationEntity> publicationEntities = new ArrayList<>();

		dtos.forEach(dto -> {
			PublicationEntity entity = (PublicationEntity) mapToEntity(dto);
			publicationEntities.add(entity);
		});
		return publicationEntities;
	}
}
