package com.direct.app.service;

import com.direct.app.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.direct.app.io.entities.UserEntity;

public interface UserService extends UserDetailsService {

	public UserEntity createUser(UserEntity userEntity) throws Exception;
	
	public UserEntity retrieveUser(String username) throws Exception;

	public Long getCurrentUserId() throws Exception;

	public String getCurrentUsername() throws Exception;

	public UserEntity getCurrentUserEntity() throws Exception;

	public UserEntity retrieveUserById(long id) throws Exception;

	public void updateUser(UserEntity user);
	
}
