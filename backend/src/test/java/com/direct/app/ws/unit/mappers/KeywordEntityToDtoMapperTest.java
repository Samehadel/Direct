package com.direct.app.ws.unit.mappers;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.mappers.EntityDTOMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.direct.app.enumerations.EntityDTOMapperType.KEYWORD_MAPPER;
import static org.junit.Assert.assertEquals;

public class KeywordEntityToDtoMapperTest {
	private EntityDTOMapper entityMapper;
	private KeywordEntity originalEntity;
	private KeywordDto resultDTO;
	private List<KeywordDto> resultDTOs;
	private List<KeywordEntity> originalEntities;
	private Random random;

	@Before
	public void init() {
		originalEntity = new KeywordEntity();
		entityMapper = EntityDTOMapperFactory.getEntityDTOMapper(KEYWORD_MAPPER);
		originalEntities = new ArrayList<>();
		random = new Random();
	}

	@After
	public void reset() {
		entityMapper = null;
	}

	@Test
	public void testKeywordEntityToDtoMapper() {
		originalEntity = generateKeywordEntity();
		resultDTO = (KeywordDto) entityMapper.mapEntityToDTO(originalEntity);

		assertEntityMatchDTO();
	}

	@Test
	public void testKeywordEntityToDtoMapper_List() {
		generateKeywordEntities();
		resultDTOs = (List<KeywordDto>) entityMapper.mapEntitiesToDTOs(originalEntities);

		assertEntitiesMatchDTOs();
	}

	private KeywordEntity generateKeywordEntity() {
		KeywordEntity keyword = new KeywordEntity();
		keyword.setId(random.nextInt());
		keyword.setDescription(String.valueOf(random.nextInt()));

		return keyword;
	}

	private void generateKeywordEntities() {
		for(int i = 0; i < 5; i++){
			originalEntities.add(generateKeywordEntity());
		}
	}

	private void assertEntityMatchDTO() {
		assertEquals(originalEntity.getId(), resultDTO.getId());
		assertEquals(originalEntity.getDescription(), resultDTO.getDescription());
	}

	private void assertEntitiesMatchDTOs() {
		for(int i = 0; i < 5; i++){
			originalEntity = originalEntities.get(i);
			resultDTO = resultDTOs.get(i);
			assertEntityMatchDTO();
		}
	}
}