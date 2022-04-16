package com.direct.app.ws.unit.service;

import com.direct.app.exceptions.ExceptionBody;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.service.ConnectionRequestService;
import com.direct.app.service.UserService;
import com.direct.app.service.implementation.ConnectionRequestServiceImplementation;
import com.direct.app.shared.dto.ConnectionRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.direct.app.exceptions.ErrorCode.U$0003;
import static com.direct.app.exceptions.ErrorCode.U$0005;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

public class ConnectionRequestServiceTest {
    @Mock
    private UserService userServiceMock;
    @Mock
    private RequestRepository connectionRequestsRepoMock;
    @Mock
    private ConnectionRepository connectionRepoMock;
    @InjectMocks
    private ConnectionRequestService connectionRequestService;

    @Before
    public void init() {
        this.connectionRequestService = new ConnectionRequestServiceImplementation();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendConnectionRequestTest() throws Exception {
        Long senderId = 1L;
        Long receiverId = 2L;

        // Mocking
        when(userServiceMock.retrieveUserById(1))
                .thenReturn(new UserEntity(senderId));
        when(userServiceMock.retrieveUserById(2))
                .thenReturn(new UserEntity(receiverId));
        when(userServiceMock.getCurrentUserId())
                .thenReturn(senderId);
        when(connectionRequestsRepoMock.save(any()))
                .thenReturn(new RequestEntity(100L));

        Long connectionRequestId = connectionRequestService.sendConnectionRequest(new ConnectionRequestDto(senderId, receiverId));

        // Assertion
        Assert.assertEquals(100L, connectionRequestId.longValue());
    }

    @Test
    public void createConnectionRequestTest_CurrentUserAndSenderMismatch() throws Exception {
        Long senderId = 1L;
        Long receiverId = 2L;

        // Mocking
        when(userServiceMock.retrieveUserById(1))
                .thenReturn(new UserEntity(senderId));
        when(userServiceMock.retrieveUserById(2))
                .thenReturn(new UserEntity(receiverId));
        when(userServiceMock.getCurrentUserId())
                .thenReturn(receiverId);

        try {
            connectionRequestService.sendConnectionRequest(new ConnectionRequestDto(senderId, receiverId));
            assertEquals(0, 1);
        } catch (RuntimeException ex) {
            ExceptionBody body = getExceptionBody(ex);
            assertEquals(U$0003, body.getErrorCode());
        }
    }

    @Test
    public void acceptConnectionRequestTest() throws Exception {
        Long senderId = 1L;
        Long receiverId = 2L;

        // Mocking
        when(connectionRequestsRepoMock.findById(1L))
                .thenReturn(Optional
                        .of(new RequestEntity(1L, createUserEntity(senderId), createUserEntity(receiverId))));
        when(userServiceMock.getCurrentUserId())
                .thenReturn(receiverId);
        when(userServiceMock.retrieveUserById(1))
                .thenReturn(new UserEntity(senderId));
        when(userServiceMock.retrieveUserById(2))
                .thenReturn(new UserEntity(receiverId));

        boolean isSucceed = connectionRequestService.acceptConnectionRequest(1L);
        assertEquals(true, isSucceed);
    }

    @Test
    public void acceptConnectionRequestTest_CurrentUserAndReceiverMismatch() throws Exception {
        Long senderId = 1L;
        Long receiverId = 2L;

        // Mocking
        when(connectionRequestsRepoMock.findById(1L))
                .thenReturn(Optional
                        .of(new RequestEntity(1L, createUserEntity(senderId), createUserEntity(receiverId))));
        when(userServiceMock.getCurrentUserId())
                .thenReturn(senderId);
        when(userServiceMock.retrieveUserById(1))
                .thenReturn(new UserEntity(senderId));
        when(userServiceMock.retrieveUserById(2))
                .thenReturn(new UserEntity(receiverId));

        try {
            connectionRequestService.acceptConnectionRequest(1L);
            assertEquals(0, 1);
        } catch (RuntimeException ex) {
            ExceptionBody body = getExceptionBody(ex);
            assertEquals(U$0005, body.getErrorCode());
        }
    }

    @Test
    public void rejectConnectionRequestTest() throws Exception {
        Long senderId = 1L;
        Long receiverId = 2L;

        // Mocking
        when(connectionRequestsRepoMock.findById(1L))
                .thenReturn(Optional
                        .of(new RequestEntity(1L, createUserEntity(senderId), createUserEntity(receiverId))));
        when(userServiceMock.getCurrentUserId())
                .thenReturn(receiverId);
        when(userServiceMock.retrieveUserById(1))
                .thenReturn(new UserEntity(senderId));
        when(userServiceMock.retrieveUserById(2))
                .thenReturn(new UserEntity(receiverId));

        boolean isSucceed = connectionRequestService.rejectConnectionRequest(1L);
        assertEquals(true, isSucceed);
    }

    @Test
    public void rejectConnectionRequestTest_CurrentUserAndReceiverMismatch() throws Exception {
        Long senderId = 1L;
        Long receiverId = 2L;

        // Mocking
        when(connectionRequestsRepoMock.findById(1L))
                .thenReturn(Optional
                        .of(new RequestEntity(1L, createUserEntity(senderId), createUserEntity(receiverId))));
        when(userServiceMock.getCurrentUserId())
                .thenReturn(senderId);
        when(userServiceMock.retrieveUserById(1))
                .thenReturn(new UserEntity(senderId));
        when(userServiceMock.retrieveUserById(2))
                .thenReturn(new UserEntity(receiverId));

        try {
            connectionRequestService.rejectConnectionRequest(1L);
            assertEquals(0, 1);
        } catch (RuntimeException ex) {
            ExceptionBody body = getExceptionBody(ex);
            assertEquals(U$0005, body.getErrorCode());
        }
    }

    @Test
    public void retrieveConnectionRequestsTest() throws Exception {
        Long userId = 1L;


        when(userServiceMock.getCurrentUserId())
                .thenReturn(userId);
        when(connectionRequestsRepoMock.findRequestsByReceiverId(userId))
                .thenReturn(createRequestsEntities());

        List<ConnectionRequestDto> connectionRequestDTOs = connectionRequestService.retrieveConnectionRequests();

        List<ConnectionRequestDto> expectedDTOs = getRequestDTOs(createRequestsEntities());

        assertEquals(expectedDTOs, connectionRequestDTOs);
    }

    private List<ConnectionRequestDto> getRequestDTOs(List<RequestEntity> requests) {
        List<ConnectionRequestDto> requestDTOs = new ArrayList<>();

        requests.forEach(req -> requestDTOs.add(convertRequestEntityToDto(req)));

        return requestDTOs;
    }

    private List<RequestEntity> createRequestsEntities() {
        List<RequestEntity> requestEntities = new ArrayList<>();
        requestEntities.add(new RequestEntity(1L, new UserEntity(1L), new UserEntity(2L)));
        requestEntities.add(new RequestEntity(2L, new UserEntity(3L), new UserEntity(1L)));
        requestEntities.add(new RequestEntity(3L, new UserEntity(1L), new UserEntity(5L)));
        requestEntities.add(new RequestEntity(4L, new UserEntity(10L), new UserEntity(1L)));

        return requestEntities;
    }

    private ConnectionRequestDto convertRequestEntityToDto(RequestEntity req) {
        ConnectionRequestDto requestDto = new ConnectionRequestDto();
        BeanUtils.copyProperties(req.getSender(), requestDto.getSenderDetails());
        BeanUtils.copyProperties(req.getSender().getUserDetails(), requestDto.getSenderDetails());
        BeanUtils.copyProperties(req.getSender().getUserDetails().getUserImage(), requestDto.getSenderDetails());
        requestDto.setId(req.getId());
        requestDto.setReceiverId(req.getReceiver().getId());
        requestDto.setSenderId(req.getSender().getId());
        return requestDto;
    }

    private ExceptionBody getExceptionBody(Exception exception) throws IOException {
        String reason = ((RuntimeBusinessException) exception).getReason().toString();
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(reason, ExceptionBody.class);
    }

    private UserEntity createUserEntity(Long userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }
}
