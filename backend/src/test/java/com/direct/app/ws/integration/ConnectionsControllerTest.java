package com.direct.app.ws.integration;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.io.dto.ConnectionRequestDto;
import com.direct.app.ws.commons.TestCommons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.direct.app.exceptions.ErrorCode.U$0001;
import static com.direct.app.exceptions.ErrorCode.U$0003;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD,  scripts={"/sql/database_cleanup.sql"})
public class ConnectionsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TestCommons testCommons;

    private static ObjectMapper mapper;
    private HttpEntity<?> request;
    private ResponseEntity response;
    private String requestBodyJson;


    @BeforeClass
    public static void init(){
        mapper = new ObjectMapper();
    }

    @After
    public void reset(){
        request = null;
        requestBodyJson = null;
        response = null;
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/sql/database_cleanup.sql", "/sql/Insert_User_Data.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
    public void send_connection_request_authToken_and_sender_id_mismatch() throws IOException {
        prepareConnectionRequestJson("username_2", "username_3");
        sendConnectionRequestByUser("username_1");

        assertEquals(NOT_ACCEPTABLE, response.getStatusCode());
        assertTrue(testCommons.responseMatchErrorCode(response, U$0003));
    }

    private void prepareConnectionRequestJson(String senderUsername, String receiverUsername) throws JsonProcessingException {
        List<UserEntity> userEntities = findUsers(new String[]{senderUsername, receiverUsername});
        ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto(
                userEntities.get(0).getId(),
                userEntities.get(1).getId());

        requestBodyJson = mapper.writeValueAsString(connectionRequestDto);
    }

    private void sendConnectionRequestByUser(String senderUsername){
        request = testCommons.getHttpEntity(requestBodyJson, senderUsername);
        response = testRestTemplate.postForEntity(
                "/requests/send",
                request,
                Object.class);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/sql/Insert_User_Data.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})
    public void send_connection_request_happy_path() throws JsonProcessingException {
        prepareConnectionRequestJson("username_1", "username_2");
        sendConnectionRequestByUser("username_1");

        prepareConnectionRequestJson("username_1", "username_3");
        sendConnectionRequestByUser("username_1");

        prepareConnectionRequestJson("username_1", "username_4");
        sendConnectionRequestByUser("username_1");

        prepareConnectionRequestJson("username_2", "username_4");
        sendConnectionRequestByUser("username_2");

        assertRequestsAsExpected();
    }

    private void assertRequestsAsExpected(){
        List<RequestEntity> allRequests = (List) requestRepository.findAll();

        assertEquals(allRequests.size(), 4);
        assertSentRequestsByUser("username_1", 3);
        assertReceivedRequestsByUser("username_4", 2);
    }

    private void assertSentRequestsByUser(String username, int sentRequests){
        UserEntity user = findUser(username);
        List<RequestEntity> requests = requestRepository.findRequestsBySenderId(user.getId());
        assertEquals(sentRequests, requests.size());
    }

    private void assertReceivedRequestsByUser(String username, int receivedRequests){
        UserEntity user = findUser(username);
        List<RequestEntity> requests = requestRepository.findRequestsByReceiverId(user.getId());
        assertEquals(receivedRequests, requests.size());
    }

    private UserEntity findUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0001, username));
        return userEntity;
    }

    private List<UserEntity> findUsers(String[] usernames) {
        List<UserEntity> users = new ArrayList<>();

        for (String username : usernames)
            users.add(findUser(username));

        return users;
    }
}
