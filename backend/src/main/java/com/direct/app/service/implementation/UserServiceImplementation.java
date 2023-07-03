package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.ProfileDto;
import com.direct.app.io.entities.UserAuthorityEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.UserAuthorityRepository;
import com.direct.app.repositery.UserDetailsRepository;
import com.direct.app.repositery.UserRepository;
import com.direct.app.redis.RedisSchema;
import com.direct.app.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    private final Logger logger = LogManager.getLogger(UserServiceImplementation.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserAuthorityRepository authRepo;

    @Autowired
    private UserDetailsRepository detailsRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserEntity createUser(UserEntity userEntity) throws Exception {
        logger.info("Started createUser for " + userEntity);
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
    @Cacheable(value = "users", key = "#username")
    public ProfileDto retrieveUser(String username) throws Exception {
        logger.info("UserService: retrieveUser for username: " + username);
        UserEntity userEntity =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        EntityDTOMapper mapper = EntityDTOMapperFactory.getEntityDTOMapper(userEntity);
        BaseDTO userDTO = mapper.mapEntityToDTO(userEntity);
        return (ProfileDto) userDTO;
    }

    @Override
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return getCurrentUserId(username);
    }

    private Long getCurrentUserId(String username){
        Long inMemoryUserId = getUserIdFromRedisCache(username);

        if(inMemoryUserId == null){
            Long dbUserId = userRepo.getUserId(username);
            ofNullable(dbUserId)
                    .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0011, username));
            setUserIdForRedisCache(username, dbUserId);
            return dbUserId;
        }

        return inMemoryUserId;
    }

    private void setUserIdForRedisCache(String username, long userId) {
        logger.info("UserServiceImpl: setUserIdForRedisCache for username:" + username);
        getRedisValueOps().set(RedisSchema.getUserIdKey(username), userId);
    }

    private Long getUserIdFromRedisCache(String username) {
        logger.info("UserServiceImpl: getUserIdFromRedisCache for username:" + username);
        Object userIdObj = getRedisValueOps().get(RedisSchema.getUserIdKey(username));

        return (Long) userIdObj;
    }

    private ValueOperations getRedisValueOps(){
        return redisTemplate.opsForValue();
    }
    @Override
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Override
    public UserEntity getCurrentUserEntity() {
        logger.info("UserServiceImpl: getCurrentUserEntity");
        String username = getCurrentUsername();

        UserEntity userEntity =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return userEntity;
    }

    @Override
    public UserEntity getCurrentUserEntity_FullData() {
        logger.info("UserServiceImpl: getCurrentUserEntity_FullData");
        String username = getCurrentUsername();
        UserEntity userEntity =
                userRepo.findByUsername_FullData(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return userEntity;
    }

    @Override
    public UserEntity retrieveUserById(long id) {
        logger.info("UserServiceImpl: retrieveUserById for id:" + id);
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
        logger.info("UserServiceImpl: loadUserByUsername for username:" + username);
        UserEntity user =
                userRepo.findByUsername(username)
                        .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0006, username));

        return new User(user.getUsername(), user.getEncryptedPassword(), new ArrayList<>());

    }
}
