package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.ConnectionRequestDto;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.service.ConnectionRequestService;
import com.direct.app.service.UserService;
import com.direct.app.shared.EntityDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.direct.app.enumerations.EntityDTOMapperType.REQUEST_MAPPER;
import static com.direct.app.exceptions.ErrorCode.*;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class ConnectionRequestServiceImplementation implements ConnectionRequestService {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestRepository connectionRequestsRepo;

    @Autowired
    private ConnectionRepository connectionRepo;

    //@Autowired
    private EntityDTOConverter converter;
    @PostConstruct
    public void init(){
        converter = new EntityDTOConverter();
    }


    @Override
    public Long sendConnectionRequest(Long receiverId) throws Exception {

        RequestEntity newRequest = new RequestEntity();

        UserEntity sender = userService.getCurrentUserEntity();
        UserEntity receiver = userService.retrieveUserById(receiverId);

        newRequest.assignConnectionRequestSender(sender);
        newRequest.assignConnectionRequestReceiver(receiver);

        validateRequestNotExistInDB(newRequest);
        // Save the request
        newRequest = connectionRequestsRepo.save(newRequest);

        return newRequest.getId();
    }

    private void validateRequestNotExistInDB(RequestEntity newRequest) {
        //TODO: add implementation
    }

    @Override
    public boolean acceptConnectionRequest(long connectionRequestId) throws Exception {
        ConnectionEntity connection = new ConnectionEntity();

        RequestEntity request = validateCurrentUserIsReceiver(connectionRequestId);

        setConnectionUsers(connection, request);

        connectionRequestsRepo.delete(request);
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
        List<ConnectionRequestDto> dtos = (List<ConnectionRequestDto>) converter.mapToDTOs(requests);
        return dtos;
    }
}
