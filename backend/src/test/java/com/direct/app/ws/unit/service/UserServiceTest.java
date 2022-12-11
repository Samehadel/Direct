package com.direct.app.ws.unit.service;

import com.direct.app.exceptions.ErrorCode;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.enumerations.UserRole;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.UserService;
import com.direct.app.service.implementation.UserServiceImplementation;
import com.direct.app.io.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());
        when(userRepo.save(any()))
                .thenReturn(userEntity);

        UserEntity user = userService.createUser(userEntity);

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
            assertEquals(U$0001, ex.getErrorCode());
        }
    }

    @Test
    public void createUser_unhappy_path_username_is_null() throws Exception {
        UserEntity userEntity = new UserEntity();

        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(userEntity)); // Mocking an exist username

        try {
            userService.createUser(userEntity);
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            assertEquals(U$0007, ex.getErrorCode());
        }
    }

    @Test
    public void retrieveUser_happy_path_test() throws Exception {
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

        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("username");

        assertNotNull(userDetails);
        assertEquals("username", userDetails.getUsername());
        assertEquals("userEncryptedPassword", userDetails.getPassword());
    }

    @Test
    public void loadUserByUsername_unhappy_path_username_not_exist() throws Exception {
        when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        try{
            userService.loadUserByUsername(anyString());
            assertEquals(1, 0);
        } catch (RuntimeBusinessException ex){
            assertEquals(U$0006, ex.getErrorCode());
        }
    }
    @Test
    public void retrieveUser_unhappy_path_username_not_exist() throws Exception {
        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());

        try {
            userService.retrieveUser("username");
            assertEquals(0, 1);
        } catch (RuntimeBusinessException ex) {
            assertEquals(U$0006, ex.getErrorCode());
        }
    }

    @Test
    public void retrieveUserById_happy_path() throws Exception {
        when(userRepo.findById(anyLong()))
                .thenReturn(Optional.of(new UserEntity()));

        UserEntity user = userService.retrieveUserById(anyLong());
        assertNotNull(user);
    }

    @Test
    public void retrieveUserById_unhappy_path_user_id_not_exist() throws Exception{
        when(userRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        try{
            UserEntity user = userService.retrieveUserById(anyLong());
            assertEquals(1, 0);
        }catch (RuntimeBusinessException ex){
            assertEquals(U$0002, ex.getErrorCode());
        }
    }

    @Test
    public void generateUserEntityFromDtoTest(){
        UserDTO userDto = new UserDTO();

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

        UserDTO generatedUserDTO = userEntity.generateUserDTOFromEntity();

        assertDTOMatchUserEntity(generatedUserDTO, userEntity);
    }
    private void assertUserEntityMatchDTO(UserEntity userEntity, UserDTO userDto){
        assertCommonField(userEntity, userDto);
        assertPasswordMatchEncryption(userDto.getPassword(), userEntity.getEncryptedPassword());
    }

    private void assertDTOMatchUserEntity(UserDTO userDto, UserEntity userEntity){
        assertCommonField(userEntity, userDto);
    }

    private void assertCommonField(UserEntity userEntity, UserDTO userDto){
        assertEquals(userDto.getId(), userEntity.getId());
        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getFirstName(), userEntity.getFirstName());
        assertEquals(userDto.getLastName(), userEntity.getLastName());
    }

    private void assertPasswordMatchEncryption(String password, String encryptedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, encryptedPassword));
    }

    private UserDTO createUserDTO() {
        UserDTO dto = new UserDTO();

        dto.setId(1l);
        dto.setFirstName("fName");
        dto.setLastName("lName");
        dto.setUsername("username");
        dto.setPassword("password");

        return dto;
    }

    private void assertExceptionWithErrorCode(RuntimeBusinessException ex, ErrorCode errorCode) throws IOException {
        assertEquals(errorCode, ex.getErrorCode());
    }
}
