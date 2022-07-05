package com.direct.app.ws.unit.mappers;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.PublicationDto;
import com.direct.app.io.dto.SenderDetails;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.mappers.impl.PublicationEntityDTOMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.direct.app.enumerations.EntityDTOMapperType.PUBLICATION_MAPPER;
import static org.junit.Assert.assertEquals;

public class PublicationEntityDTOMapperTest {
	private EntityDTOMapper mapper;

	@Before
	public void initialize(){
		mapper = EntityDTOMapperFactory.getEntityDTOMapper(PUBLICATION_MAPPER);
	}

	@After
	public void reset(){
		this.mapper = null;
	}

	@Test
	public void testPublicationDtoToEntityMapper(){
		mapper = new PublicationEntityDTOMapper();
		PublicationDto publicationDTO = generatePublicationDTO();
		PublicationEntity resultEntity = (PublicationEntity) mapper.mapDtoToEntity(publicationDTO);

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
