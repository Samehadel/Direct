package com.direct.app.ws.unit.service;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.io.entities.UserRole;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.UserService;
import com.direct.app.service.implementation.UserServiceImplementation;
import com.direct.app.shared.Utils;
import com.direct.app.shared.dto.UserDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    UserRepository userRepo;

    @Mock
    UserAuthorityRepository authRepo;

    @Mock
    UserDetailsRepository detailsRepo;

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    Utils utils;

    @InjectMocks
    private UserService userService;

    @Before
    public void init() {
        this.userService = new UserServiceImplementation();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUser_happy_path() throws Exception {
        UserDto userDto = createUserDTO();

        // Mocking Stage 1
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        when(userRepo.save(any()))
                .thenReturn(createUserEntity(userDto));
        when(encoder.encode(anyString()))
                .thenReturn("encryptedPassword");
        when(utils.generateUserId(anyInt()))
                .thenReturn("0123456789");

        UserEntity user = userService.createUser(userDto);

        assertEquals("encryptedPassword", user.getEncryptedPassword());
        assertEquals(10, user.getVirtualUserId().length());
        assertEquals(user.getAuthority().getRole(), UserRole.ROLE_USER.name());
    }

    @Test
    public void createUser_unhappy_path_username_already_exist() throws Exception {
        UserDto userDto = createUserDTO();

        // Mocking Stage
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(new UserEntity())); // Mocking an exist username

        // Assertion Stage
        try {
            userService.createUser(userDto);
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            System.out.println(ex.getReason());
            assertEquals(1, 1);
        }
    }

    @Test
    public void retrieveUser_happy_path_test() throws Exception {
        UserDto userDto = createUserDTO();

        // Mocking Stage
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(createUserEntity(userDto)));

        UserEntity user = userService.retrieveUser("username");

        assertNotNull(user);
    }

    @Test
    public void loadUserByUsername_happy_path() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setEncryptedPassword("userEncryptedPassword");

        // Mocking Stage
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("username");

        assertNotNull(userDetails);
        assertEquals("username", userDetails.getUsername());
        assertEquals("userEncryptedPassword", userDetails.getPassword());
    }

    @Test
    public void loadUserByUsername_unhappy_path_username_not_exist() throws Exception {
        // Mocking Stage
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        try{
            userService.loadUserByUsername(anyString());
            assertEquals(1, 0);
        } catch (RuntimeBusinessException ex){
            System.out.println(ex.getReason());
            assertEquals(1, 1);
        }
    }
    @Test
    public void retrieveUser_unhappy_path_username_not_exist() throws Exception {
        // Mocking Stage
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());

        // Assertion Stage
        try {
            userService.retrieveUser("username");
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            System.out.println(ex.getReason());
            assertEquals(0, 0);
        }
    }

    @Test
    public void retrieveUserById_happy_path() throws Exception {
        // Mocking Stage
        when(userRepo.findById(anyLong()))
                .thenReturn(Optional.of(new UserEntity()));

        // Assertion Stage
        UserEntity user = userService.retrieveUserById(anyLong());
        assertNotNull(user);
    }

    @Test
    public void retrieveUserById_unhappy_path_user_id_not_exist() throws Exception{
        // Mocking Stage
        when(userRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        try{
            UserEntity user = userService.retrieveUserById(anyLong());
            assertEquals(1, 0);
        }catch (RuntimeBusinessException ex){
            System.out.println(ex.getReason());
            assertEquals(1, 1);
        }
    }

    private UserDto createUserDTO() {
        UserDto dto = new UserDto();

        dto.setId(1l);
        dto.setFirstName("fName");
        dto.setLastName("lName");
        dto.setUsername("username");
        dto.setPassword("password");

        return dto;
    }

    private UserEntity createUserEntity(UserDto dto) {
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(dto, userEntity);

        return userEntity;
    }
}
