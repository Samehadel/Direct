package com.company.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.company.app.io.entities.UserEntity;
import com.company.app.shared.dto.UserDto;

public interface IUserService extends UserDetailsService {

	public UserDto createUser(UserDto userDto) throws Exception;
	
	public UserEntity retrieveUser(String username) throws Exception;

	public long retrieveUserId() throws Exception;

	public UserEntity retrieveUser(long id) throws Exception;

	public UserDto updateUser(UserEntity user);
	
}
