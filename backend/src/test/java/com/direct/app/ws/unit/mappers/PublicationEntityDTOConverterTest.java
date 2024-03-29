package com.direct.app.ws.unit.mappers;

import com.direct.app.io.dto.PublicationDto;
import com.direct.app.io.dto.SenderDetails;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.impl.PublicationEntityDTOMapper;
import com.direct.app.shared.EntityDTOConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PublicationEntityDTOConverterTest {
	private EntityDTOConverter converter;

	@Before
	public void initialize(){
		converter = new EntityDTOConverter();
	}

	@Test
	public void testPublicationDtoToEntityMapper(){
		PublicationDto publicationDTO = generatePublicationDTO();
		PublicationEntity resultEntity = (PublicationEntity) converter.mapToEntity(publicationDTO);

		assertEntityMatchDTO(resultEntity, publicationDTO);
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

	private void assertEntityMatchDTO(PublicationEntity resultEntity, PublicationDto originalDTO) {
		assertEquals(originalDTO.getId(), resultEntity.getId());
		assertEquals(originalDTO.getSenderId(), resultEntity.getSender().getId());
		assertSenderDetails(originalDTO.getSenderDetails(), resultEntity);
		assertEquals(originalDTO.getContent(), resultEntity.getContent());
		assertEquals(originalDTO.getLink(), resultEntity.getLink());
		assertEquals(originalDTO.getIsRead(), resultEntity.isRead());
	}

	private void assertSenderDetails(SenderDetails senderDetails, PublicationEntity resultEntity) {
		UserEntity sender = resultEntity.getSender();
		UserDetailsEntity userDetails = resultEntity.getSender().getUserDetails();
		assertEquals(senderDetails.getFirstName(), sender.getFirstName());
		assertEquals(senderDetails.getLastName(), sender.getLastName());
		assertEquals(senderDetails.getProfessionalTitle(), userDetails.getProfessionalTitle());
	}

}
