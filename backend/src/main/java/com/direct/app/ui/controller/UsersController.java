package com.direct.app.ui.controller;

import com.direct.app.security.SecurityConstants;
import com.direct.app.service.IUserService;
import com.direct.app.shared.dto.UserDto;
import com.direct.app.ui.models.request.SignupRequestModel;
import com.direct.app.ui.models.response.UserResponseModel;
import com.direct.app.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	IUserService userService;

	@Autowired
	JwtUtils jwtUtils;
	
	
	@PostMapping("/signup")
	public ResponseEntity<UserResponseModel> createUser
            (@RequestBody SignupRequestModel userDetails) throws Exception {

		// Initialize required objects
		UserResponseModel returnObj = new UserResponseModel();
		UserDto userDto = new UserDto();

		// Copy values From request body to the dto object
		BeanUtils.copyProperties(userDetails, userDto);

		// Use the user service
		UserDto createdUserEntity = userService.createUser(userDto);

		if(createdUserEntity == null)
			return ResponseEntity.status(500)
					.header("Message", "Email Already Exist")
					.build();

		BeanUtils.copyProperties(createdUserEntity, returnObj);

        // Add JWT and virtualUserId then return the response entity
		ResponseEntity response = ResponseEntity.status(HttpStatus.OK)
                .header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX +
                        jwtUtils.getJWT(userDetails.getUserName(), userDetails.getPassword()))
                .header("virtualUserId", createdUserEntity.getVirtualUserId())
                .body(returnObj);

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
