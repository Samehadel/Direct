package com.direct.app.ws.unit.controllers;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.security.SecurityConstants;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.UserDto;
import com.direct.app.ui.controller.UsersController;
import com.direct.app.utils.JwtUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UsersControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UsersController usersController;

    @Before
    public void init(){
        this.usersController = new UsersController();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_user_test_happy_path() throws Exception {
        UserDto userDto = new UserDto(
                null,
                "Sameh",
                "Adel",
                "email",
                "pass");

        // Mocking Stage
        when(userService.createUser(any())).thenReturn(new UserEntity());

        ResponseEntity responseEntity = usersController.createUser(userDto);

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(responseEntity.getHeaders().get(SecurityConstants.HEADER_STRING));
    }
}