package com.direct.app.service.implementation;

import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.service.INetworkService;
import com.direct.app.service.IUserService;
import com.direct.app.ui.models.response.ProfileResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NetworkServiceImplementation implements INetworkService {

    @Autowired
    private ConnectionRepository connectionRepo;

    @Autowired
    private IUserService userService;

    @Override
    public Set<ProfileResponseModel> retrieveNetwork() throws Exception {
        long userId = userService.retrieveUserId();

        // Get user's connections from DB
        List<ConnectionEntity> connections = connectionRepo.findByUserId(userId);

        // Initialize the set to hold the response data
        Set<ProfileResponseModel> responseModels = new HashSet<>();

        // Convert each entity to the response model
        for(ConnectionEntity conn: connections){
            UserEntity user;
            if(conn.getFirstUser().getId() != userId){
                user = conn.getFirstUser();
            }else{
                user = conn.getSecondUser();
            }

            responseModels.add(new ProfileResponseModel(conn.getId(),
                    user.getFirstName(),
                    user.getLastName()));
        }

        return responseModels;
    }

    @Override
    public boolean removeConnection(long connectionId) {
        int check = connectionRepo.removeConnection(connectionId);

        return check != 1 ? true : false;
    }
}
