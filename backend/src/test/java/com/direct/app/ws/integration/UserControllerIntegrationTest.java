package com.direct.app.ws.integration;

import com.direct.app.security.SecurityConstants;
import com.direct.app.shared.dto.UserDto;
import com.direct.app.ui.controller.UsersController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UsersController usersController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void create_user_test_happy_path() throws Exception {
        UserDto userDto = new UserDto(
                null,
                "Sameh",
                "Adel",
                "email",
                "pass");

        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity("/users/signup", userDto, UserDto.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getHeaders().get(SecurityConstants.HEADER_STRING));

        Assert.assertEquals(responseEntity.getBody().getFirstName(), userDto.getFirstName());
        Assert.assertEquals(responseEntity.getBody().getLastName(), userDto.getLastName());
        Assert.assertEquals(responseEntity.getBody().getUsername(), userDto.getUsername());
    }

    @Test
    public void create_user_test_unhappy_path() throws Exception {
        String username = "username";

        signupUser(username); // To make the user already exist

        UserDto userDto = new UserDto(
                null,
                "fName",
                "lName", username,
                "pass");

        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity("/users/signup", userDto, UserDto.class);

        Assert.assertNotEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    private void signupUser(String username){
        UserDto userDto = new UserDto(
                null,
                "fName",
                "lName",
                username,
                "pass");

        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity("/users/signup", userDto, UserDto.class);
    }
}
