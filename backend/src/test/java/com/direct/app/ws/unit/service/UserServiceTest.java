package com.direct.app.ws.unit.service;

import com.direct.app.exceptions.ErrorCode;
import com.direct.app.exceptions.ExceptionBody;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.enumerations.UserRole;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.UserService;
import com.direct.app.service.implementation.UserServiceImplementation;
import com.direct.app.shared.Utils;
import com.direct.app.shared.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

import static com.direct.app.exceptions.ErrorCode.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepo;

    @Mock
    private UserAuthorityRepository authRepo;

    @Mock
    private UserDetailsRepository detailsRepo;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private Utils utils;

    @Autowired
    private ObjectMapper mapper;

    @InjectMocks
    private UserService userService;

    @Before
    public void init() {
        this.userService = new UserServiceImplementation();
        mapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUser_happy_path() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");

        // Mocking Stage 1
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        when(userRepo.save(any()))
                .thenReturn(userEntity);
         when(utils.generateUserId(anyInt()))
                .thenReturn("0123456789");

        UserEntity user = userService.createUser(userEntity);

        assertEquals(10, user.getVirtualUserId().length());
        assertEquals(user.getAuthority().getRole(), UserRole.ROLE_USER.name());
        assertNotNull(user.getUserDetails());
        assertNotNull(user.getAuthority());
    }

    @Test
    public void createUser_unhappy_path_username_already_exist() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");

        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(userEntity)); // Mocking an exist username

        try {
            userService.createUser(userEntity);
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            assertExceptionWithErrorCode(ex, U$0001);
        }
    }

    @Test
    public void createUser_unhappy_path_username_is_null() throws Exception {
        UserEntity userEntity = new UserEntity();

        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(userEntity)); // Mocking an exist username

        // Assertion Stage
        try {
            userService.createUser(userEntity);
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            assertExceptionWithErrorCode(ex, U$0007);
        }
    }

    @Test
    public void retrieveUser_happy_path_test() throws Exception {
        // Mocking Stage
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(new UserEntity()));

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
            assertExceptionWithErrorCode(ex, U$0006);
        }
    }
    @Test
    public void retrieveUser_unhappy_path_username_not_exist() throws Exception {
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());

        try {
            userService.retrieveUser("username");
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            assertExceptionWithErrorCode(ex, U$0006);
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
            assertExceptionWithErrorCode(ex, U$0002);
        }
    }

    @Test
    public void generateUserEntityFromDtoTest(){
        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setUsername("username");
        userDto.setFirstName("fName");
        userDto.setLastName("lName");
        userDto.setPassword("password");

        UserEntity generatedUserEntity = userDto.generateUserEntityFromDTO();

        assertUserEntityMatchDTO(generatedUserEntity, userDto);
    }

    @Test
    public void generateDTOFromUserEntityTest(){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setUsername("username");
        userEntity.setFirstName("fName");
        userEntity.setLastName("lName");
        userEntity.setEncryptedPassword("EncryptedPassword");

        UserDto generatedUserDTO = userEntity.generateUserDTOFromEntity();

        assertDTOMatchUserEntity(generatedUserDTO, userEntity);
    }
    private void assertUserEntityMatchDTO(UserEntity userEntity, UserDto userDto){
        assertCommonField(userEntity, userDto);
        assertPasswordMatchEncryption(userDto.getPassword(), userEntity.getEncryptedPassword());
    }

    private void assertDTOMatchUserEntity(UserDto userDto, UserEntity userEntity){
        assertCommonField(userEntity, userDto);
    }

    private void assertCommonField(UserEntity userEntity, UserDto userDto){
        assertEquals(userDto.getId(), userEntity.getId());
        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getFirstName(), userEntity.getFirstName());
        assertEquals(userDto.getLastName(), userEntity.getLastName());
    }

    private void assertPasswordMatchEncryption(String password, String encryptedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, encryptedPassword));
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

    private void assertExceptionWithErrorCode(ResponseStatusException ex, ErrorCode errorCode) throws IOException {
        ExceptionBody exceptionBody = mapper.readValue(ex.getReason(), ExceptionBody.class);
        assertEquals(errorCode, exceptionBody.getErrorCode());
    }

    private UserEntity createUserEntity(UserDto dto) {
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(dto, userEntity);

        return userEntity;
    }
}
