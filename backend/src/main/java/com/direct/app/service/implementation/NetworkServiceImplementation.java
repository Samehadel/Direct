package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.service.NetworkService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.direct.app.enumerations.EntityDTOMapperType.USER_MAPPER;
import static com.direct.app.exceptions.ErrorCode.U$0008;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class NetworkServiceImplementation implements NetworkService {

    @Autowired
    private ConnectionRepository connectionRepo;

    @Autowired
    private UserService userService;

    private EntityDTOMapper entityDTOMapper;

    @Override
    public Set<ProfileDto> retrieveNetwork() throws Exception {
        Long userId = userService.getCurrentUserId();
        List<ConnectionEntity> connections = connectionRepo.findByUserId(userId);
        Set<ProfileDto> profileDTOs = new HashSet<>();
        entityDTOMapper = EntityDTOMapperFactory.getEntityDTOMapper(USER_MAPPER);

        for(ConnectionEntity conn: connections){
            UserEntity otherUser = getOtherUserInConnection(conn);
            ProfileDto profileDTO = (ProfileDto) entityDTOMapper.mapEntityToDTO(otherUser);

            profileDTOs.add(profileDTO);
        }

        return profileDTOs;
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
