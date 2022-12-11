package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOConverterFacade;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.service.NetworkService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.direct.app.exceptions.ErrorCode.U$0008;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class NetworkServiceImplementation implements NetworkService {

    @Autowired
    private ConnectionRepository connectionRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityDTOConverterFacade converter;

    @Override
    public List<ProfileDto> retrieveNetwork() throws Exception {
        Long userId = userService.getCurrentUserId();
        List<ConnectionEntity> connections = connectionRepo.findByUserId(userId);

        return (List<ProfileDto>) converter.mapToDTOs(connections);
    }

    private UserEntity getOtherUserInConnection(ConnectionEntity connection) throws Exception {
        Long userId = userService.getCurrentUserId();

        if(connection.getFirstUser().getId() != userId){
            return connection.getFirstUser();
        }else{
            return connection.getSecondUser();
        }
    }

    @Override
    public void removeConnection(Long connectionId) throws Exception {
        Long currentUserId = userService.getCurrentUserId();

        ConnectionEntity connectionEntity =
                connectionRepo.findByConnectionIdAndUserId(connectionId, currentUserId)
                .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0008, connectionId, currentUserId));

        connectionRepo.delete(connectionEntity);
    }
}
