package com.direct.app.ui.controller;

import com.direct.app.service.IProfilesService;
import com.direct.app.ui.models.request.ProfileDetailsRequestModel;
import com.direct.app.ui.models.response.ProfileResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfilesController {

	@Autowired
	IProfilesService profilesService; 
	

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
	
	@PutMapping("/details/{userId}")
	public void editAccountInfo(@RequestBody ProfileDetailsRequestModel profileDetails,
								@PathVariable long userId){
		
	}
}
