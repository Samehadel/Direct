package com.direct.app.ws.unit.utils;

import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.DtoToEntityMapper;
import com.direct.app.mappers.impl.PublicationDtoToEntityMapper;
import com.direct.app.shared.SenderDetails;
import com.direct.app.shared.dto.PublicationDto;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DtoToEntityConvertersTest {
	private DtoToEntityMapper mapper;

	@After
	public void reset(){
		this.mapper = null;
	}

	@Test
	public void testPublicationDtoToEntityMapper(){
		mapper = new PublicationDtoToEntityMapper();
		PublicationDto publicationDto = generatePublicationDTO();
		PublicationEntity resultEntity = (PublicationEntity) mapper.mapToEntity(publicationDto);

		assertEntityMatchDTO(resultEntity, publicationDto);
	}

	private PublicationDto generatePublicationDTO(){
		PublicationDto publicationDto = new PublicationDto();

		publicationDto.setId(1L);
		publicationDto.setSenderId(100L);
		publicationDto.setSenderDetails(generateSenderDetails());
		publicationDto.setContent("Random Content");
		publicationDto.setLink("Random Link");
		publicationDto.setIsRead(true);

		return publicationDto;
	}

	private SenderDetails generateSenderDetails(){
		SenderDetails senderDetails = new SenderDetails();
		senderDetails.setFirstName("fName");
		senderDetails.setLastName("lName");
		senderDetails.setProfessionalTitle("pTitle");

		return senderDetails;
	}

	private void assertEntityMatchDTO(PublicationEntity resultEntity, PublicationDto publicationDto) {
		assertEquals(publicationDto.getId(), resultEntity.getId());
		assertEquals(publicationDto.getSenderId(), resultEntity.getSender().getId());
		assertSenderDetails(publicationDto.getSenderDetails(), resultEntity);
		assertEquals(publicationDto.getContent(), resultEntity.getContent());
		assertEquals(publicationDto.getLink(), resultEntity.getLink());
		assertEquals(publicationDto.getIsRead(), resultEntity.isRead());
	}

	private void assertSenderDetails(SenderDetails senderDetails, PublicationEntity resultEntity) {
		UserEntity sender = resultEntity.getSender();
		UserDetailsEntity userDetails = resultEntity.getSender().getUserDetails();
		assertEquals(senderDetails.getFirstName(), sender.getFirstName());
		assertEquals(senderDetails.getLastName(), sender.getLastName());
		assertEquals(senderDetails.getProfessionalTitle(), userDetails.getProfessionalTitle());
	}

}
