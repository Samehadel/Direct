package com.direct.app.ui.controller;

import com.direct.app.io.entities.UserEntity;
import com.direct.app.security.SecurityConstants;
import com.direct.app.service.UserService;
import com.direct.app.io.dto.UserDTO;
import com.direct.app.shared.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
    UserService userService;

	@Autowired
	JwtTokenGenerator jwtTokenGenerator;
	
	
	@PostMapping("/signup")
	public ResponseEntity<UserDTO> createUser
            (@RequestBody UserDTO userDto) throws Exception {

		UserEntity createdUserEntity = userService.createUser(userDto.generateUserEntityFromDTO());

		ResponseEntity response =
				ResponseEntity.status(HttpStatus.OK)
                	.header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX +
                        	jwtTokenGenerator.generateJwtByUsername(userDto.getUsername()))
                	.body(createdUserEntity.generateUserDTOFromEntity());

		return response;
	}
	
	@PutMapping("/pin/{userId}")
	public String pinUserAccess(@PathVariable long userId) {
		return userId + " Pined";
	}
	
	@GetMapping("/test")
	public boolean isAuthenticated() {
		return true;
	}
}
