package com.direct.app.mappers.impl;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.shared.SenderDetails;
import com.direct.app.shared.dto.BaseDTO;
import com.direct.app.shared.dto.ConnectionRequestDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

public class RequestEntityToDtoMapper implements EntityToDtoMapper {
	private UserEntity sender;
	private UserEntity receiver;
	private ConnectionRequestDto resultDto;
	private RequestEntity sourceEntity;

	@Override
	public BaseDTO mapToDTO(BaseEntity entity) {
		resultDto = new ConnectionRequestDto();
		sourceEntity = (RequestEntity) entity;

		ofNullable(sourceEntity)
				.map(RequestEntity::getId)
				.ifPresent(requestId -> resultDto.setId(requestId));

		setRequestUsers();

		return resultDto;
	}

	private void setRequestUsers() {
		setRequestReceiver();
		setRequestSender();
	}

	private void setRequestReceiver() {
		receiver = sourceEntity.getReceiver();
		ofNullable(receiver)
				.map(UserEntity::getId)
				.ifPresent(receiverId -> resultDto.setReceiverId(receiverId));
	}

	private void setRequestSender() {
		sender = sourceEntity.getSender();
		ofNullable(sender)
				.map(UserEntity::getId)
				.ifPresent(id -> resultDto.setSenderId(id));

		setRequestSenderDetails();
	}

	private void setRequestSenderDetails() {
		SenderDetails senderDetails = new SenderDetails();

		ofNullable(sender.getUserDetails())
				.map(UserDetailsEntity::getProfessionalTitle)
				.ifPresent(title -> senderDetails.setProfessionalTitle(title));

		ofNullable(sender)
				.map(UserEntity::getFirstName)
				.ifPresent(fName -> senderDetails.setFirstName(fName));

		ofNullable(sender)
				.map(UserEntity::getLastName)
				.ifPresent(lName -> senderDetails.setLastName(lName));

		resultDto.setSenderDetails(senderDetails);
	}

	@Override
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities) {
		List<ConnectionRequestDto> resultDTOs = new ArrayList<>();

		entities.forEach(entity -> {
			ConnectionRequestDto resultDTO = (ConnectionRequestDto) mapToDTO(entity);
			resultDTOs.add(resultDTO);
		});
		return resultDTOs;
	}
}
