package com.direct.app.service;

import com.direct.app.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.direct.app.io.entities.UserEntity;

public interface IUserService extends UserDetailsService {

	public UserDto createUser(UserDto userDto) throws Exception;
	
	public UserEntity retrieveUser(String username) throws Exception;

	public long retrieveUserId() throws Exception;

	public UserEntity retrieveUser(long id) throws Exception;

	public UserDto updateUser(UserEntity user);
	
}
