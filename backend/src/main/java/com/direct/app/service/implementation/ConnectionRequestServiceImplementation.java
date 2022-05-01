package com.direct.app.service.implementation;

import com.direct.app.exceptions.ErrorCode;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.service.ConnectionRequestService;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.ConnectionRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.direct.app.exceptions.ErrorCode.U$0004;
import static com.direct.app.exceptions.ErrorCode.U$0005;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class ConnectionRequestServiceImplementation implements ConnectionRequestService {

    @Autowired
    UserService userService;

    @Autowired
    RequestRepository connectionRequestsRepo;

    @Autowired
    ConnectionRepository connectionRepo;

    @Override
    public Long sendConnectionRequest(ConnectionRequestDto connectionRequestDto) throws Exception {

        validateCurrentUserIsSender(connectionRequestDto.getSenderId());
        RequestEntity newRequest = new RequestEntity();

        UserEntity sender = userService.retrieveUserById(connectionRequestDto.getSenderId());
        UserEntity receiver = userService.retrieveUserById(connectionRequestDto.getReceiverId());

        newRequest.assignConnectionRequestSender(sender);
        newRequest.assignConnectionRequestReceiver(receiver);

        // Save the request
        newRequest = connectionRequestsRepo.save(newRequest);

        return newRequest.getId();
    }

    private void validateCurrentUserIsSender(Long senderId) throws Exception {
        Long currentUserId = userService.getCurrentUserId();

        if (!currentUserId.equals(senderId))
            throw new RuntimeBusinessException(NOT_ACCEPTABLE, ErrorCode.U$0003, senderId);

    }

    @Override
    public boolean acceptConnectionRequest(long connectionRequestId) throws Exception {
        ConnectionEntity connection = new ConnectionEntity();

        RequestEntity request = validateCurrentUserIsReceiver(connectionRequestId);

        setConnectionUsers(connection, request);

        // Delete the request from the request table
        connectionRequestsRepo.delete(request);

        // Save the connection in connections table
        connectionRepo.save(connection);


        return true;
    }

    private void setConnectionUsers(ConnectionEntity connection, RequestEntity request) throws Exception {
        UserEntity sender = userService.retrieveUserById(request.getSender().getId());
        UserEntity receiver = userService.retrieveUserById(request.getReceiver().getId());

        connection.setFirstUser(sender);
        connection.setSecondUser(receiver);
    }
    private RequestEntity validateCurrentUserIsReceiver(Long connectionRequestId) throws Exception {
        Long currentUserId = userService.getCurrentUserId();
        RequestEntity request =
                connectionRequestsRepo
                        .findById(connectionRequestId)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0004, connectionRequestId));

        if (request.getReceiver().getId() != currentUserId)
            throw new RuntimeBusinessException(NOT_ACCEPTABLE, U$0005, request.getId(), currentUserId);

        return request;
    }

    @Override
    public boolean rejectConnectionRequest(long connectionRequestId) throws Exception {
        validateCurrentUserIsReceiver(connectionRequestId);
        connectionRequestsRepo.deleteById(connectionRequestId);
        return true;
    }

    @Override
    public List<ConnectionRequestDto> retrieveConnectionRequests() throws Exception {

        long userId = userService.getCurrentUserId();

        List<RequestEntity> requests = connectionRequestsRepo.findRequestsByReceiverId(userId);

        return getRequestDTOs(requests);
    }

    private List<ConnectionRequestDto> getRequestDTOs(List<RequestEntity> requests){
        List<ConnectionRequestDto> requestDTOs = new ArrayList<>();

        requests.forEach(req -> {
            requestDTOs.add(convertRequestEntityToDto(req));
        });

        return requestDTOs;
    }

    // TODO: create Request Entity To Dto mapper
    private ConnectionRequestDto convertRequestEntityToDto(RequestEntity req){
        ConnectionRequestDto requestDto = new ConnectionRequestDto();
        BeanUtils.copyProperties(req.getSender(), requestDto.getSenderDetails());
        BeanUtils.copyProperties( ofNullable(req.getSender().getUserDetails()).orElse(new UserDetailsEntity()), requestDto.getSenderDetails());

        requestDto.setId(req.getId());
        requestDto.setReceiverId(req.getReceiver().getId());
        requestDto.setSenderId(req.getSender().getId());
        return requestDto;
    }
}
