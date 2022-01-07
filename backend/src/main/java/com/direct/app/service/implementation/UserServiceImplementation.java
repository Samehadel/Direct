package com.direct.app.service.implementation;

import com.direct.app.io.entities.*;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.service.IUserService;
import com.direct.app.shared.Utils;
import com.direct.app.shared.dto.UserDto;
import com.direct.app.ui.models.request.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImplementation implements IUserService {

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
    public UserDto createUser(UserDto userDto) throws Exception {

        // Check If Email Already Exist
        if (userRepo.findByUserName(userDto.getUserName()) != null)
            return null;

        // Prepare Required objects
        UserDto returnDto = new UserDto();

        UserEntity user = new UserEntity();
        UserAuthorityEntity authorities = new UserAuthorityEntity(UserRole.ROLE_USER.name());
        UserDetailsEntity userDetails = new UserDetailsEntity();
        UserImageEntity userImage = new UserImageEntity();

        // Copy Values From DTO To User Entity
        BeanUtils.copyProperties(userDto, user);

        // Relationship Exchange
        user.setAuthority(authorities);
        authorities.setUser(user);

        user.setUserDetails(userDetails);
        userDetails.setUser(user);

        userDetails.setUserImage(userImage);
        userImage.setUserDetails(userDetails);

        // Encrypt User Password - Then -> Generate Random User Id
        user.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        user.setVirtualUserId(utils.generateUserId(10));

        // Use Repository To Save The User And Its Role
        userRepo.save(user);
        authRepo.save(authorities);
        detailsRepo.save(userDetails);

        // Copy Values From Result Entity to The Return Object
        BeanUtils.copyProperties(user, returnDto);

        return returnDto;
    }

    @Override
    public UserEntity retrieveUser(String username) throws Exception {
        UserEntity userEntity = userRepo.findByUserName(username);

        if (userEntity == null)
            throw new Exception(ErrorMessages.USER_NOT_FOUND.getErrorMessage());

        return userEntity;
    }

    @Override
    public long retrieveUserId() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepo.getUserId(username);
    }


    @Override
    public UserEntity retrieveUser(long id) throws Exception {
        UserEntity userEntity = userRepo.findById(id);

        if (userEntity == null)
            throw new Exception(ErrorMessages.USER_NOT_FOUND.getErrorMessage());

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
    public UserDetails loadUserByUsername(String userName) {
        UserEntity user = userRepo.findByUserName(userName);

        if (user != null)
            return new User(user.getUserName(), user.getEncryptedPassword(), new ArrayList<>());
        else
            throw new UsernameNotFoundException(userName);
    }


}
