package com.direct.app.ui.controller;

import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.service.IProfilesService;
import com.direct.app.ui.models.request.ProfileDetailsRequestModel;
import com.direct.app.ui.models.response.ProfileResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfilesController {

	@Autowired
	private IProfilesService profilesService;


	/**
			* This endpoint is used to show similar profiles for a specific user.
			* @return A list of similar profiles returned using a response model called ProfileResponseModel
	 */

	@GetMapping
	public ResponseEntity displayProfiles() throws Exception {

		// Use of the service
		List<ProfileResponseModel> users = profilesService.retrieveSimilarUsers();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(users);
	}
	
	@PutMapping("/details")
	public void editAccountInfo(@RequestBody ProfileDetailsRequestModel profileDetails){
		
	}

	@PostMapping("/image")
	public ResponseEntity editAccountImage(@RequestParam("image") MultipartFile image) throws Exception {

		boolean check = profilesService.editAccountImage(image);

		return check ? ResponseEntity
							.status(HttpStatus.OK)
							.build() :
						ResponseEntity
							.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.build();
	}

	@GetMapping("/details")
	public ResponseEntity getAccountImage() throws Exception {

	    ProfileResponseModel responseModel = new ProfileResponseModel();

	    // Service call
		UserEntity userEntity = profilesService.getAccountDetails();
		UserDetailsEntity userDetails = userEntity.getUserDetails();

		// Copy properties back to response model
		BeanUtils.copyProperties(userEntity, responseModel);
        BeanUtils.copyProperties(userDetails, responseModel);
        BeanUtils.copyProperties(userDetails.getUserImage(), responseModel);

		return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseModel);
	}
}
