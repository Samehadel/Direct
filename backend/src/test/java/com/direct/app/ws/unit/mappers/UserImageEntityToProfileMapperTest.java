package com.direct.app.ws.unit.mappers;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.ProfileImageDTO;
import com.direct.app.io.entities.UserImageEntity;
import com.direct.app.mappers.EntityDTOMapper;
import org.junit.Before;
import org.junit.Test;

import static com.direct.app.enumerations.EntityDTOMapperType.USER_IMAGE_MAPPER;
import static org.junit.Assert.assertEquals;

public class UserImageEntityToProfileMapperTest {
	private EntityDTOMapper entityMapper;
	private UserImageEntity originalEntity;
	private ProfileImageDTO resultDTO;

	@Before
	public void init() {
		originalEntity = new UserImageEntity();
		entityMapper = EntityDTOMapperFactory.getEntityDTOMapper(USER_IMAGE_MAPPER);
	}

	@Test
	public void testUserEntityToDtoMapper() {
		generateImageEntity();
		resultDTO = (ProfileImageDTO) entityMapper.mapEntityToDTO(originalEntity);

		assertEntityMatchDTO();
	}

	private UserImageEntity generateImageEntity() {
		originalEntity.setId(1L);
		originalEntity.setImageName("imgName");
		originalEntity.setImageFormat("imgFormat");
		originalEntity.setImageUrl("imgUrl.JPG");

		return originalEntity;
	}

	private void assertEntityMatchDTO() {
		assertEquals(originalEntity.getId(), resultDTO.getId());
		assertEquals(originalEntity.getImageName(), resultDTO.getImageName());
		assertEquals(originalEntity.getImageFormat(), resultDTO.getImageFormat());
		assertEquals(originalEntity.getImageUrl(), resultDTO.getImageUrl());
	}
}
