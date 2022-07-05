package com.direct.app.mappers.impl;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.PublicationDto;
import com.direct.app.io.dto.SenderDetails;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class PublicationEntityDTOMapper implements EntityDTOMapper {
	private UserDetailsEntity senderDetailsEntity;
	private UserEntity sender;
	private PublicationDto publicationDto;
	private PublicationEntity publicationEntity;

	@Override
	public BaseDTO mapEntityToDTO(BaseEntity entity) {
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
	public List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities) {
		return entities.stream()
				.map(this::mapEntityToDTO)
				.collect(toList());
	}

	@Override
	public BaseEntity mapDtoToEntity(BaseDTO dto) {
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
	public List<? extends BaseEntity> mapDTOsToEntities(List<? extends BaseDTO> dtos) {
		List<PublicationEntity> publicationEntities = new ArrayList<>();

		dtos.forEach(dto -> {
			PublicationEntity entity = (PublicationEntity) mapDtoToEntity(dto);
			publicationEntities.add(entity);
		});
		return publicationEntities;
	}

}
