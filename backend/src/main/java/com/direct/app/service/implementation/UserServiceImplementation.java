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

import static com.direct.app.exceptions.ErrorCode.U$0001;
import static com.direct.app.exceptions.ErrorCode.U$0002;
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
    public UserEntity createUser(UserDto userDTO) throws Exception {

        // Check If Email Already Exist
        userRepo.findByUsername(userDTO.getUsername())
                    .ifPresent(userEntity -> {
                        throw new RuntimeBusinessException(NOT_ACCEPTABLE, U$0001, userDTO.getUsername());
                    });

        // Prepare Required objects
        UserDto returnDto = new UserDto();

        UserEntity user = new UserEntity();
        UserAuthorityEntity authorities = new UserAuthorityEntity(UserRole.ROLE_USER.name());
        UserDetailsEntity userDetails = new UserDetailsEntity();
        UserImageEntity userImage = new UserImageEntity();

        // Copy Values From DTO To User Entity
        BeanUtils.copyProperties(userDTO, user);

        // Relationship Exchange
        user.setAuthority(authorities);
        authorities.setUser(user);

        user.setUserDetails(userDetails);
        userDetails.setUser(user);

        userDetails.setUserImage(userImage);
        userImage.setUserDetails(userDetails);

        // Encrypt User Password - Then -> Generate Random User Id
        user.setEncryptedPassword(encoder.encode(userDTO.getPassword()));
        user.setVirtualUserId(utils.generateUserId(10));

        // Use Repository To Save The User And Its Role
        userRepo.save(user);
        authRepo.save(authorities);
        detailsRepo.save(userDetails);

        // Copy Values From Result Entity to The Return Object
        BeanUtils.copyProperties(user, returnDto);

        return user;
    }

    @Override
    public UserEntity retrieveUser(String username) throws Exception {
        UserEntity userEntity =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0001, username));

        return userEntity;
    }

    @Override
    public Long getCurrentUserId() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepo.getUserId(username);
    }


    @Override
    public UserEntity retrieveUserById(long id) throws Exception {
        UserEntity userEntity =
                userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0002, id));

        return userEntity;
    }


    @Override
    public UserDto updateUser(UserEntity user) {

        UserDto userDto = new UserDto();
        UserEntity backUser = userRepo.save(user);

        BeanUtils.copyProperties(backUser, userDto);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0002, username));

        return new User(user.getUsername(), user.getEncryptedPassword(), new ArrayList<>());

    }


}
