package com.direct.app.ws.integration;

import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.shared.dto.PublicationDto;
import com.direct.app.ws.integration.commons.TestCommons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
		scripts = {"/sql/database_cleanup.sql",
				"/sql/Insert_User_Data.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
public class PublicationsControllerTest {

	private final String PUBLISH_JOB_POST_URL = "/publications/publish";
	private final String RETRIEVE_JOB_POSTS_URL = "/publications";
	private final String MARK_PUBLICATION_AS_READ_URL = "/publications/status/read/";
	private final String MARK_PUBLICATION_AS_UNREAD_URL = "/publications/status/unread/";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private TestCommons testCommons;

	@Autowired
	private PublicationsRepository publicationsRepo;

	private ObjectMapper mapper;

	private Long senderId;
	private String requestBodyJson;
	private ResponseEntity response;

	@Before
	public void init() {
		this.mapper = new ObjectMapper();
	}

	@After
	public void reset(){
		this.senderId = null;
		this.requestBodyJson = null;
		this.response = null;
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {"/sql/database_cleanup.sql",
					"/sql/Insert_User_Data.sql",
					"/sql/publications/Publications_Test_Data_1.sql"})
	public void publishJobPost_happy_path_test() throws Exception {
		this.senderId = 1003L;

		setPublicationJsonForPostRequest();
		sendPublicationRequest();

		assertEquals(this.response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {	"/sql/database_cleanup.sql",
						"/sql/Insert_User_Data.sql",
						"/sql/publications/Publications_Test_Data_2.sql"})
	public void checkPublicationReceiversAsExpected() throws Exception {
		this.senderId = 1003L;

		setPublicationJsonForPostRequest();
		sendPublicationRequest();
		assertPublicationReceivers();
	}

	private void sendPublicationRequest() {
		String senderUsername = "username_4";
		String authToken = testCommons.generateUserAuthToken(senderUsername);
		HttpEntity req = testCommons.getHttpEntity(this.requestBodyJson, authToken);
		this.response = testRestTemplate.postForEntity(PUBLISH_JOB_POST_URL, req, Object.class);
	}

	private void assertPublicationReceivers() {
		Set<PublicationEntity> publications = publicationsRepo.findPublicationsBySenderId(this.senderId);
		List<Long> receiversIds = Arrays.asList(1000L, 1001L);

		assertEquals(2, publications.size());
		checkPublicationReceiversIds(publications, receiversIds);
	}

	private void checkPublicationReceiversIds(Set<PublicationEntity> publications, List<Long> receiversIds) {
		receiversIds.forEach(id -> {
			publicationsHasReceiverWithId(publications, id);
		});
	}

	private void publicationsHasReceiverWithId(Set<PublicationEntity> publications, Long receiverId) {
		Long count = publications
				.stream()
				.filter(pub -> pub.getReceiver().getId().equals(receiverId))
				.count();

		assertEquals(1L, count.longValue());
	}

	@Test
	public void accessInboxPublications_happy_path_test() throws Exception {

	}

	private void setPublicationJsonForPostRequest() throws JsonProcessingException {
		PublicationDto publicationDTO = new PublicationDto();

		publicationDTO.setSenderId(this.senderId);
		publicationDTO.setContent("Publication Content Test");
		publicationDTO.setLink("Publication Link Test");
		publicationDTO.setIsRead(false);
		publicationDTO.setKeywords(Arrays.asList(800, 801));

		this.requestBodyJson = mapper.writeValueAsString(publicationDTO);
	}

	private JSONArray getPublicationKeywordsJson() {
		return testCommons.
				jsonArray()
				.put(800)
				.put(801);
	}
}
