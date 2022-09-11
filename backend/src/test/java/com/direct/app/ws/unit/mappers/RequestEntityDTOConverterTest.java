package com.direct.app.ws.unit.mappers;

import com.direct.app.io.dto.ConnectionRequestDto;
import com.direct.app.io.dto.SenderDetails;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.shared.EntityDTOConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestEntityDTOConverterTest {
	private EntityDTOConverter converter;
	private RequestEntity originalEntity;
	private ConnectionRequestDto resultDTO;

	@Before
	public void init() {
		originalEntity = new RequestEntity();
		converter = new EntityDTOConverter();
	}

	@Test
	public void testRequestEntityToDtoMapper() {
		generateRequestEntity();
		resultDTO = (ConnectionRequestDto) converter.mapToDTO(originalEntity);

		assertEntityMatchDTO();
	}

	private void generateRequestEntity() {
		originalEntity.setId(1L);
		originalEntity.setSender(generateSenderEntity());
		originalEntity.setReceiver(generateReceiverEntity());
	}

	private UserEntity generateSenderEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("fName");
		userEntity.setLastName("lName");
		userEntity.setUserDetails(generateSenderDetailsEntity());

		return userEntity;
	}

	private UserEntity generateReceiverEntity() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(2L);
		return userEntity;
	}

	private UserDetailsEntity generateSenderDetailsEntity() {
		UserDetailsEntity detailsEntity = new UserDetailsEntity();

		detailsEntity.setProfessionalTitle("pTitle");

		return detailsEntity;
	}

	private void assertEntityMatchDTO(){
		assertEquals(originalEntity.getId(), resultDTO.getId());
		assertEquals(originalEntity.getSender().getId(), resultDTO.getSenderId());
		assertEquals(originalEntity.getReceiver().getId(), resultDTO.getReceiverId());
		assertSenderDetails();
	}

	private void assertSenderDetails(){
		UserEntity senderEntity = originalEntity.getSender();
		SenderDetails senderDetailsDto = resultDTO.getSenderDetails();
		assertEquals(senderEntity.getFirstName(), senderDetailsDto.getFirstName());
		assertEquals(senderEntity.getLastName(), senderDetailsDto.getLastName());
		assertEquals(senderEntity.getUserDetails().getProfessionalTitle(), senderDetailsDto.getProfessionalTitle());
	}
}
