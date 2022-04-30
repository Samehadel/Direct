package com.direct.app.ui.controller;

import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.service.ProfilesService;
import com.direct.app.shared.dto.ProfileDetailsDto;
import com.direct.app.shared.dto.ProfileDto;
import com.direct.app.utils.copy_factory.UserEntityCopyFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfilesController {

	@Autowired
	private ProfilesService profilesService;

	// TODO: change name
	@GetMapping
	public ResponseEntity displaySimilarProfilesForUser() throws Exception {
		List<ProfileDto> profileDTOs = new ArrayList<>();
		Set<UserEntity> similarUsers = profilesService.retrieveSimilarUsers();

		similarUsers.forEach(similarUser -> {
			ProfileDto model = UserEntityCopyFactory.copyProfileDTOFromEntity(similarUser);
			profileDTOs.add(model);
		});

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileDTOs);
	}
	
	@PutMapping("/details")
	public void editAccountInfo(@RequestBody ProfileDetailsDto profileDetails){
		
	}

	@PostMapping("/image")
	public ResponseEntity editAccountImage(@RequestParam("image") MultipartFile image) throws Exception {

		boolean check = profilesService.setAccountImage(image);

		return check ? ResponseEntity
							.status(HttpStatus.OK)
							.build() :
						ResponseEntity
							.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.build();
	}

	@GetMapping("/details")
	public ResponseEntity getAccountImage() throws Exception {

	    ProfileDto responseModel = new ProfileDto();

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
