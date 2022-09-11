package com.direct.app.ws.integration;

import com.direct.app.io.dto.ProfileDto;
import com.direct.app.ws.commons.TestCommons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfilesControllerTest {

	private final String FIND_SIMILAR_USERS_URL = "/profiles";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private TestCommons testCommons;

	private ObjectMapper mapper;

	@Before
	public void init() {
		this.mapper = new ObjectMapper();
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {"/sql/database_cleanup.sql",
					   "/sql/profiles/Profiles_Test_Data_1.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void findSimilarUsersTest_user_1(){
		String senderUsername = "username_1";
		HttpEntity req = testCommons.getHttpEntity(senderUsername);
		ResponseEntity<List<ProfileDto>> exchange = testRestTemplate.exchange(FIND_SIMILAR_USERS_URL, GET, req, new ParameterizedTypeReference<List<ProfileDto>>() {});

		assertEquals(2, exchange.getBody().size());
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {"/sql/database_cleanup.sql",
					   "/sql/profiles/Profiles_Test_Data_1.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void findSimilarUsersTest_user_2(){
		String senderUsername = "username_2";
		HttpEntity req = testCommons.getHttpEntity(senderUsername);
		ResponseEntity<List<ProfileDto>> exchange = testRestTemplate.exchange(FIND_SIMILAR_USERS_URL, GET, req, new ParameterizedTypeReference<List<ProfileDto>>() {});

		assertEquals(2, exchange.getBody().size());
	}

}