package com.direct.app.ws.integration;

import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.security.SecurityConstants;
import com.direct.app.ui.models.request.ConnectionRequestModel;
import com.direct.app.ui.models.request.SignupRequestModel;
import com.direct.app.ui.models.response.ConnectionResponseModel;
import com.direct.app.ui.models.response.UserResponseModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    public void send_connection_request_happy_path() {
        String[] usernames = {"username1", "username2", "username3", "username4"};
        List<String> authTokens = signupUsers(usernames);

        List<UserEntity> userEntities = findUsers(usernames);

        // Prepare The Request
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.HEADER_STRING, authTokens.get(0));
        ConnectionRequestModel connectionModel = new ConnectionRequestModel(userEntities.get(0).getId(), userEntities.get(1).getId());
        HttpEntity<ConnectionResponseModel> request = new HttpEntity(connectionModel, headers);

        // Send Request To Create New Connection (User_1 --sends to--> User_2)
        ResponseEntity<ConnectionResponseModel> response = testRestTemplate.postForEntity("/connections/request",
                                                                                              request,
                                                                                              ConnectionResponseModel.class);
        // Assertion Stage 1
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Prepare The Request
        headers.add(SecurityConstants.HEADER_STRING, authTokens.get(0));
        connectionModel = new ConnectionRequestModel(userEntities.get(0).getId(), userEntities.get(2).getId());
        request = new HttpEntity(connectionModel, headers);

        // Send Request To Create New Connection (User_1 --sends to--> User_3)
        response = testRestTemplate.postForEntity("/connections/request",
                                                       request,
                                                       ConnectionResponseModel.class);
        // Assertion Stage 2
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Prepare The Request
        headers.add(SecurityConstants.HEADER_STRING, authTokens.get(0));
        connectionModel = new ConnectionRequestModel(userEntities.get(0).getId(), userEntities.get(3).getId());
        request = new HttpEntity(connectionModel, headers);

        // Send Request To Create New Connection (User_1 --sends to--> User_4)
        response = testRestTemplate.postForEntity("/connections/request",
                                                      request,
                                                      ConnectionResponseModel.class);

        // Assertion Stage 3
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);


        // Prepare The Request
        headers.add(SecurityConstants.HEADER_STRING, authTokens.get(1));
        connectionModel = new ConnectionRequestModel(userEntities.get(1).getId(), userEntities.get(3).getId());
        request = new HttpEntity(connectionModel, headers);

        // Send Request To Create New Connection (User_2 --sends to--> User_4)
        response = testRestTemplate.postForEntity("/connections/request",
                                                      request,
                                                      ConnectionResponseModel.class);
        // Assertion Stage 4
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        List<RequestEntity> allRequests = (List) requestRepository.findAll();
        Assert.assertEquals(allRequests.size(), 4);

        // User_1 Must Has Sent 3 Connection Requests
        List<RequestEntity> requests = requestRepository.findSenderByUserId(userEntities.get(0).getId());
        Assert.assertEquals(requests.size(), 3);

        // User_4 Must Has Received 2 Connection Requests
        requests = requestRepository.findReceiverByUserId(userEntities.get(3).getId());
        Assert.assertEquals(requests.size(), 2);

    }

    private List<String> signupUsers(String[] usernames) {
        SignupRequestModel signupRequestModel;
        List<String> authTokens = new ArrayList<>();

        for (String username : usernames) {
            signupRequestModel = new SignupRequestModel("fName", "lName",
                    username, "pass");
            ResponseEntity response = testRestTemplate.postForEntity("/users/signup", signupRequestModel, UserResponseModel.class);
            String token = response.getHeaders().get(SecurityConstants.HEADER_STRING).get(0);
            authTokens.add(token);
        }
        return authTokens;
    }
    private List<UserEntity> findUsers(String [] usernames){
        List<UserEntity> users = new ArrayList<>();

        for (String username: usernames) {
            UserEntity entity = userRepository.findByUserName(username);
            users.add(entity);
        }

        return users;
    }
}
