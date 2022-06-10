package com.direct.app.ws.integration;

import com.direct.app.security.SecurityConstants;
import com.direct.app.io.dto.UserDto;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("/test.application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = { "/sql/database_cleanup.sql",
				    "/sql/publications/Publications_Test_Data_1.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/sql/database_cleanup.sql"})

public class UserControllerTest {

    private final String USER_SIGN_UP_URL = "/users/signup";

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

        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity(USER_SIGN_UP_URL, userDto, UserDto.class);

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

        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity(USER_SIGN_UP_URL, userDto, UserDto.class);

        Assert.assertNotEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    private void signupUser(String username){
        UserDto userDto = new UserDto(
                null,
                "fName",
                "lName",
                username,
                "pass");

        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity(USER_SIGN_UP_URL, userDto, UserDto.class);
    }
}
