package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.UserAuthorityEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.direct.app.enumerations.UserRole.ROLE_USER;
import static com.direct.app.exceptions.ErrorCode.*;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserAuthorityRepository authRepo;

    @Autowired
    UserDetailsRepository detailsRepo;


    @Override
    public UserEntity createUser(UserEntity userEntity) throws Exception {
        checkIfUsernameExists(userEntity.getUsername());

        UserAuthorityEntity authorities = new UserAuthorityEntity(ROLE_USER.name());
        UserDetailsEntity userDetails = new UserDetailsEntity();

        userEntity.setAuthority(authorities);
        authorities.setUser(userEntity);

        userEntity.setUserDetails(userDetails);


        userRepo.save(userEntity);
        authRepo.save(authorities);
        detailsRepo.save(userDetails);

        return userEntity;
    }

    private void checkIfUsernameExists(String username){
        ofNullable(username)
                .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0007));

        userRepo.findByUsername(username)
                .ifPresent(e -> {
                    throw new RuntimeBusinessException(NOT_ACCEPTABLE, U$0001, username);
                });
    }

    @Override
    public UserEntity retrieveUser(String username) throws Exception {
        UserEntity userEntity =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return userEntity;
    }

    @Override
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepo.getUserId(username);
    }

    @Override
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Override
    public UserEntity getCurrentUserEntity() {
        String username = getCurrentUsername();

        UserEntity userEntity =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return userEntity;
    }

    @Override
    public UserEntity getCurrentUserEntity_FullData() {
        String username = getCurrentUsername();
        UserEntity userEntity =
                userRepo.findByUsername_FullData(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return userEntity;
    }

    @Override
    public UserEntity retrieveUserById(long id) {
        UserEntity userEntity =
                userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0002, id));
        return userEntity;
    }

    @Override
    public void updateUser(UserEntity user) {
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return new User(user.getUsername(), user.getEncryptedPassword(), new ArrayList<>());

    }
}
