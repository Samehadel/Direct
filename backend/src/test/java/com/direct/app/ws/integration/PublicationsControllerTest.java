package com.direct.app.ws.integration;

import com.direct.app.service.PublicationsService;
import com.direct.app.shared.dto.PublicationDto;
import com.direct.app.ui.controller.PublicationsController;
import com.direct.app.utils.JwtUtils;
import com.direct.app.ws.integration.commons.TestCommons;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

import java.util.Map;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
		scripts = {	"/sql/database_cleanup.sql",
					"/sql/Insert_User_Data.sql",
					"/sql/Insert_Publications_Data.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
public class PublicationsControllerTest {

	private final String PUBLISH_JOB_POST_URL = "/publications/publish";
	private final String RETRIEVE_JOB_POSTS_URL = "/publications";
	private final String  MARK_PUBLICATION_AS_READ_URL = "/publications/status/read/";
	private final String  MARK_PUBLICATION_AS_UNREAD_URL = "/publications/status/unread/";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private TestCommons testCommons;

	@Test
	public void publishJobPost_happy_path_test() throws Exception {
		Long senderId = 1003L;
		String[] senderUsername = {"username_4"};
		Map<String, String> authTokens = testCommons.generateUsersAuthTokens(senderUsername);
		String json = getPublicationDTOForPostRequestJson(senderId);

		HttpEntity req = testCommons.getHttpEntity(json, authTokens.get("username_4"));
		ResponseEntity res = testRestTemplate.postForEntity(PUBLISH_JOB_POST_URL, req, Object.class);

		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void accessInboxPublications_happy_path_test() throws Exception {


		//ResponseEntity responseEntity = publicationsController.accessInboxPublications();

		// Assertion Stage
		//Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	private String getPublicationDTOForPostRequestJson(Long senderId) throws JSONException {
		return testCommons
				.json()
					.put("sender_id", senderId)
					.put("content", "Publication Content Test")
					.put("link", "publication Link Test")
					.put("is_read", false)
					.put("keywords", getPublicationKeywordsJson())
					.toString();
	}

	private JSONArray getPublicationKeywordsJson() {
		return	testCommons.
					jsonArray()
							.put(800)
							.put(801);
	}
}
