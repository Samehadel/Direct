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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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
    public void createUser_happy_path_test() throws Exception {
        UserDto userDto = createDto();

        // Mocking Stage 1
        when(userRepo.findByUsername(anyString())).thenReturn(null);
        when(userRepo.save(any())).thenReturn(createUserEntity(userDto));
        when(encoder.encode(anyString())).thenReturn("encryptedPassword");
        when(utils.generateUserId(anyInt())).thenReturn("0123456789");

        UserEntity user = userService.createUser(userDto);

        // Assertion Stage 1
        Assert.assertEquals("encryptedPassword", user.getEncryptedPassword());
        Assert.assertEquals(10, user.getVirtualUserId().length());
        Assert.assertEquals(user.getAuthority().getRole(), UserRole.ROLE_USER.name());
    }

    @Test
    public void createUser_unhappy_path_test() throws Exception {
        UserDto userDto = createDto();

        // Mocking Stage
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(new UserEntity())); // Mocking an exist username

        // Assertion Stage
        try {
            userService.createUser(userDto);
            Assert.assertEquals(0, 1);
        } catch (Exception ex) {
            Assert.assertEquals(0, 0);
        }
    }

    @Test
    public void retrieveUser_happy_path_test() throws Exception {
        UserDto userDto = createDto();

        // Mocking Stage
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.of(createUserEntity(userDto)));
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(createUserEntity(userDto)));

        UserEntity user = userService.retrieveUser("username");
        UserEntity backUserEntity = userService.retrieveUser(userDto.getId());

        // Assertion Stage
        Assert.assertNotNull(user);
        Assert.assertNotNull(backUserEntity);
    }

    @Test
    public void retrieveUser_unhappy_path_test() throws Exception {
        // Mocking Stage
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        // Assertion Stage
        try {
            UserEntity user = userService.retrieveUser("username");
            Assert.assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            Assert.assertEquals(0, 0);
        }

        try {
            UserEntity backUserEntity = userService.retrieveUser(1L);
            Assert.assertEquals(0, 1);
        }catch (Exception ex){
            Assert.assertEquals(0, 0);
        }
    }

    private UserDto createDto() {
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
