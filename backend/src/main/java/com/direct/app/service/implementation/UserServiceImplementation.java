package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.*;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.UserService;
import com.direct.app.shared.Utils;
import com.direct.app.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    Utils utils;

    @Override
    public UserEntity createUser(UserEntity userEntity) throws Exception {
        checkIfUsernameExists(userEntity.getUsername());

        UserAuthorityEntity authorities = new UserAuthorityEntity(UserRole.ROLE_USER.name());
        UserDetailsEntity userDetails = new UserDetailsEntity();
        UserImageEntity userImage = new UserImageEntity();


        // Relationship Exchange
        userEntity.setAuthority(authorities);
        authorities.setUser(userEntity);

        userEntity.setUserDetails(userDetails);
        userDetails.setUser(userEntity);

        userDetails.setUserImage(userImage);
        userImage.setUserDetails(userDetails);

        userEntity.setVirtualUserId(utils.generateUserId(10));

        // Use Repository To Save The User And Its Role
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
    public Long getCurrentUserId() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepo.getUserId(username);
    }

    @Override
    public String getCurrentUsername() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Override
    public UserEntity getCurrentUserEntity() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserEntity userEntity =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return userEntity;
    }

    @Override
    public UserEntity retrieveUserById(long id) throws Exception {
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
