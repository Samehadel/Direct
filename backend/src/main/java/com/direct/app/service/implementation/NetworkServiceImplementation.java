package com.direct.app.service.implementation;

import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.service.NetworkService;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.ProfileDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NetworkServiceImplementation implements NetworkService {

    @Autowired
    private ConnectionRepository connectionRepo;

    @Autowired
    private UserService userService;

    @Override
    public Set<ProfileDto> retrieveNetwork() throws Exception {
        long userId = userService.retrieveUserId();

        // Get user's connections from DB
        List<ConnectionEntity> connections = connectionRepo.findByUserId(userId);

        // Initialize the set to hold the response data
        Set<ProfileDto> responseModels = new HashSet<>();

        // Convert each entity to the response model
        for(ConnectionEntity conn: connections){
            UserEntity user;
            if(conn.getFirstUser().getId() != userId){
                user = conn.getFirstUser();
            }else{
                user = conn.getSecondUser();
            }
            ProfileDto responseModel = new ProfileDto();
            BeanUtils.copyProperties(user, responseModel);
            BeanUtils.copyProperties(user.getUserDetails(), responseModel);
            BeanUtils.copyProperties(user.getUserDetails().getUserImage(), responseModel);

            responseModels.add(responseModel);
        }

        return responseModels;
    }

    @Override
    public boolean removeConnection(long connectionId) {
        int check = connectionRepo.removeConnection(connectionId);

        return check != 1 ? true : false;
    }
}
