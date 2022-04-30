package com.direct.app.ws.integration;

import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.shared.dto.PublicationDto;
import com.direct.app.ws.integration.commons.TestCommons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;


@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PublicationsControllerTest {

	private final String PUBLISH_JOB_POST_URL = "/publications/publish";
	private final String RETRIEVE_JOB_POSTS_URL = "/publications";
	private final String MARK_PUBLICATION_AS_READ_URL = "/publications/status/read";
	private final String MARK_PUBLICATION_AS_UNREAD_URL = "/publications/status/unread";

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
	public void reset() {
		this.senderId = null;
		this.requestBodyJson = null;
		this.response = null;
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {"/sql/database_cleanup.sql",
					"/sql/Insert_User_Data.sql",
					"/sql/publications/Publications_Test_Data_1.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void publishJobPost_happy_path_test() throws Exception {
		this.senderId = 1003L;

		setPublicationJsonForPostRequest();
		sendPublicationRequest();

		assertEquals(this.response.getStatusCode(), OK);
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {"/sql/database_cleanup.sql",
					"/sql/Insert_User_Data.sql",
					"/sql/publications/Publications_Test_Data_2.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void checkPublicationReceiversAsExpected() throws Exception {
		this.senderId = 1003L;

		setPublicationJsonForPostRequest();
		sendPublicationRequest();
		assertPublicationReceivers();
	}

	private void sendPublicationRequest() {
		String senderUsername = "username_4";
		HttpEntity req = testCommons.getHttpEntity(this.requestBodyJson, senderUsername);
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
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {	"/sql/database_cleanup.sql",
						"/sql/Insert_User_Data.sql",
						"/sql/publications/Publications_Test_Data_3.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void accessInboxPublications_happy_path_test() {
		accessPublicationsInboxRequest();
		assertInboxPublications();
	}

	private void accessPublicationsInboxRequest() {
		String senderUsername = "username_1";
		HttpEntity req = testCommons.getHttpEntity(this.requestBodyJson, senderUsername);
		this.response = testRestTemplate.exchange(RETRIEVE_JOB_POSTS_URL, GET, req, Object.class);
	}

	private void assertInboxPublications() {
		assertEquals(this.response.getStatusCode(), OK);
		checkInboxPublicationsAsExpected();
	}

	private void checkInboxPublicationsAsExpected() {
		List<PublicationEntity> publications = publicationsRepo.findByReceiverId(1000L);
		List<Long> publicationsIds = Arrays.asList(2000L, 2001L);

		assertEquals(2, publications.size());
		publications.forEach(pub -> {
			assertTrue(publicationsIds.contains(pub.getId()));
		});
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {	"/sql/database_cleanup.sql",
						"/sql/Insert_User_Data.sql",
						"/sql/publications/Publications_Test_Data_3.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void markPublicationAsRead_happy_path_test(){
		Long publicationId = 2000L;
		String username = "username_1";
		markPublicationAsReadRequest(publicationId, username);
		assertPublicationMarkedAsRead(publicationId);
	}

	private void markPublicationAsReadRequest(Long publicationId, String username){
		HttpEntity req = testCommons.getHttpEntity(this.requestBodyJson, username);
		this.response = testRestTemplate.exchange(MARK_PUBLICATION_AS_READ_URL.concat("?id=") + publicationId, PUT, req, Object.class);
	}

	private void assertPublicationMarkedAsRead(Long publicationId){
		PublicationEntity publication = publicationsRepo.findById(publicationId).get();

		assertEquals(OK, this.response.getStatusCode());
		assertEquals(true, publication.isRead());
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {	"/sql/database_cleanup.sql",
						"/sql/Insert_User_Data.sql",
						"/sql/publications/Publications_Test_Data_3.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void markPublicationAsRead_happy_path_incompatible_user(){
		Long publicationId = 2000L;
		String username = "username_2";

		markPublicationAsReadRequest(publicationId, username);
		assertEquals(NOT_ACCEPTABLE, this.response.getStatusCode());
	}

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
			scripts = {	"/sql/database_cleanup.sql",
						"/sql/Insert_User_Data.sql",
						"/sql/publications/Publications_Test_Data_3.sql"})
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
	public void markPublicationAsUnRead_happy_path_test(){
		Long publicationId = 2001L;
		String username = "username_1";
		markPublicationAsUnReadRequest(publicationId, username);
		assertPublicationMarkedAsUnRead(publicationId);
	}

	private void markPublicationAsUnReadRequest(Long publicationId, String username){
		HttpEntity req = testCommons.getHttpEntity(this.requestBodyJson, username);
		this.response = testRestTemplate.exchange(MARK_PUBLICATION_AS_UNREAD_URL.concat("?id=") + publicationId, PUT, req, Object.class);
	}

	private void assertPublicationMarkedAsUnRead(Long publicationId){
		PublicationEntity publication = publicationsRepo.findById(publicationId).get();

		assertEquals(OK, this.response.getStatusCode());
		assertEquals(false, publication.isRead());
	}

	@Test
	public void checkAllRequestsAuthorization() {
		final Map<String, HttpMethod> URLs = getControllerURLs();

		URLs.forEach((url, httpMethod) -> {
			sendRequestWithInvalidAuthorization(url, httpMethod);
			assertEquals(FORBIDDEN, this.response.getStatusCode());
		});
	}

	private Map<String, HttpMethod> getControllerURLs(){
		final Map<String, HttpMethod> URLs = new HashMap<>();
		URLs.put(PUBLISH_JOB_POST_URL, POST);
		URLs.put(RETRIEVE_JOB_POSTS_URL, GET);
		URLs.put(MARK_PUBLICATION_AS_READ_URL, PUT);
		URLs.put(MARK_PUBLICATION_AS_UNREAD_URL, PUT);

		return URLs;
	}

	private void sendRequestWithInvalidAuthorization(final String URL, HttpMethod method) {
		String senderUsername = "username_1111";
		HttpEntity req = testCommons.getHttpEntity(this.requestBodyJson, senderUsername);

		this.response = testRestTemplate.exchange(URL, method, req, Object.class);
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
}
