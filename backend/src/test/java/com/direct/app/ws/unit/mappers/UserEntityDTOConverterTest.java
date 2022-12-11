package com.direct.app.ws.unit.mappers;

import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.mappers.impl.UserEntityDTOMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;

public class UserEntityDTOConverterTest {
	private EntityDTOMapper entityMapper;
	private UserEntity originalEntity;
	private ProfileDto resultDTO;

	@Before
	public void init() {
		originalEntity = new UserEntity();
		entityMapper = UserEntityDTOMapper.getInstance(new BCryptPasswordEncoder());
	}

	@After
	public void reset() {
		entityMapper = null;
	}

	@Test
	public void testUserEntityToDtoMapper() {
		generateUserEntity();
		resultDTO = (ProfileDto) entityMapper.mapEntityToDTO(originalEntity);

		assertEntityMatchDTO();
	}

	private UserEntity generateUserEntity() {
		originalEntity.setId(1L);
		originalEntity.setFirstName("fName");
		originalEntity.setLastName("lName");
		originalEntity.setUserDetails(generateUserDetails());

		return originalEntity;
	}

	private UserDetailsEntity generateUserDetails() {
		UserDetailsEntity userDetails = new UserDetailsEntity();

		userDetails.setPhone("phone");
		userDetails.setMajorField("mField");
		userDetails.setBio("bio");
		userDetails.setProfessionalTitle("pTitle");

		return userDetails;
	}

	private void assertEntityMatchDTO() {
		assertEquals(originalEntity.getId(), resultDTO.getId());
		assertEquals(originalEntity.getFirstName(), resultDTO.getFirstName());
		assertEquals(originalEntity.getLastName(), resultDTO.getLastName());
		assertUserDetails();
	}

	private void assertUserDetails() {
		UserDetailsEntity userDetails = originalEntity.getUserDetails();
		assertEquals(userDetails.getPhone(), resultDTO.getPhone());
		assertEquals(userDetails.getMajorField(), resultDTO.getMajorField());
		assertEquals(userDetails.getBio(), resultDTO.getBio());
		assertEquals(userDetails.getProfessionalTitle(), resultDTO.getProfessionalTitle());
	}

}
