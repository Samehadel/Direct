package com.direct.app.ws.integration;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.security.SecurityConstants;
import com.direct.app.shared.dto.ConnectionRequestDto;
import com.direct.app.shared.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.direct.app.exceptions.ErrorCode.U$0001;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD,  scripts={"/sql/database_cleanup.sql"})
public class ConnectionsControllerIntegrationTest {

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

    public void addHeader_AuthorizationToken(HttpHeaders headers, String token){
        headers.remove(SecurityConstants.HEADER_STRING);
        headers.add(SecurityConstants.HEADER_STRING, token);
    }
    @Test
    public void send_connection_request_authToken_and_sender_id_mismatch(){
        String[] usernames = {"username1", "username2", "username3"};
        Map<String, String> authTokens = signupUsers(usernames);
        List<UserEntity> userEntities = findUsers(usernames);

        // Prepare The Request
        HttpHeaders headers = new HttpHeaders();
        addHeader_AuthorizationToken(headers, authTokens.get(usernames[0]));
        ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto(
                userEntities.get(1).getId(),
                userEntities.get(2).getId());
        HttpEntity<?> request = new HttpEntity(connectionRequestDto, headers);

        ResponseEntity<ConnectionRequestDto> response = testRestTemplate.postForEntity(
                "/requests/send",
                request,
                ConnectionRequestDto.class);

        Assert.assertEquals(NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void send_connection_request_happy_path() {
        String[] usernames = {"username1", "username2", "username3", "username4"};
        Map<String, String> authTokens = signupUsers(usernames);

        List<UserEntity> userEntities = findUsers(usernames);

        // Prepare The Request
        HttpHeaders headers = new HttpHeaders();
        addHeader_AuthorizationToken(headers, authTokens.get(usernames[0]));
        ConnectionRequestDto connectionRequestDto = new ConnectionRequestDto(
                userEntities.get(0).getId(),
                userEntities.get(1).getId());
        HttpEntity<?> request = new HttpEntity(connectionRequestDto, headers);

        // Send Request To Create New Connection (User_1 --sends to--> User_2)
        ResponseEntity<ConnectionRequestDto> response = testRestTemplate.postForEntity(
                "/requests/send",
                request,
                ConnectionRequestDto.class);
        // Assertion Stage 1
        Assert.assertEquals(OK, response.getStatusCode());

        // Prepare The Request
        addHeader_AuthorizationToken(headers, authTokens.get(usernames[0]));
        connectionRequestDto = new ConnectionRequestDto(
                userEntities.get(0).getId(),
                userEntities.get(2).getId());
        request = new HttpEntity(connectionRequestDto, headers);

        // Send Request To Create New Connection (User_1 --sends to--> User_3)
        response = testRestTemplate.postForEntity(
                "/requests/send",
                request,
                ConnectionRequestDto.class);

        // Assertion Stage 2
        Assert.assertEquals(OK, response.getStatusCode());

        // Prepare The Request
        addHeader_AuthorizationToken(headers, authTokens.get(usernames[0]));
        connectionRequestDto = new ConnectionRequestDto(
                userEntities.get(0).getId(),
                userEntities.get(3).getId());
        request = new HttpEntity(connectionRequestDto, headers);

        // Send Request To Create New Connection (User_1 --sends to--> User_4)
        response = testRestTemplate.postForEntity(
                "/requests/send",
                request,
                ConnectionRequestDto.class);

        // Assertion Stage 3
        Assert.assertEquals( OK, response.getStatusCode());


        // Prepare The Request
        addHeader_AuthorizationToken(headers, authTokens.get(usernames[1]));
        connectionRequestDto = new ConnectionRequestDto(
                userEntities.get(1).getId(),
                userEntities.get(3).getId());
        request = new HttpEntity(connectionRequestDto, headers);

        // Send Request To Create New Connection (User_2 --sends to--> User_4)
        response = testRestTemplate.postForEntity("/requests/send",
                request,
                ConnectionRequestDto.class);
        // Assertion Stage 4
        Assert.assertEquals(OK, response.getStatusCode());

        List<RequestEntity> allRequests = (List) requestRepository.findAll();
        Assert.assertEquals(allRequests.size(), 4);

        // User_1 Must has Sent 3 Connection Requests
        List<RequestEntity> requests = requestRepository.findRequestsBySenderId(userEntities.get(0).getId());
        Assert.assertEquals(3, requests.size());

        // User_4 Must Has Received 2 Connection Requests
        requests = requestRepository.findRequestsByReceiverId(userEntities.get(3).getId());
        Assert.assertEquals(2, requests.size());

    }


    private Map<String, String> signupUsers(String[] usernames) {
        UserDto userDto;
        Map<String, String> authTokens = new HashMap<>();

        for (String username : usernames) {
            userDto = new UserDto(
                    null,
                    "fName",
                    "lName",
                    username,
                    "pass");
            ResponseEntity response = testRestTemplate.postForEntity("/users/signup", userDto, UserDto.class);
            String token = response.getHeaders().get(SecurityConstants.HEADER_STRING).get(0);
            authTokens.put(username, token);
        }
        return authTokens;
    }

    private List<UserEntity> findUsers(String[] usernames) {
        List<UserEntity> users = new ArrayList<>();

        for (String username : usernames) {
            UserEntity entity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0001, username));
            users.add(entity);
        }

        return users;
    }
}
