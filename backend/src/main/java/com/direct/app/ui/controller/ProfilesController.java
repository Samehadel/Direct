package com.direct.app.ui.controller;

import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.service.ProfilesService;
import com.direct.app.shared.dto.ProfileDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfilesController {
	@Autowired
	private ProfilesService profilesService;


	@GetMapping
	public ResponseEntity findSimilarProfilesForUser() throws Exception {
		Set<ProfileDto> similarUsers = profilesService.retrieveSimilarUsers();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(similarUsers);
	}

	@PostMapping("/image")
	public ResponseEntity editAccountImage(@RequestParam("url") String imageUrl) throws Exception {
		profilesService.setAccountImage(imageUrl);

		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}

	@GetMapping("/details")
	public ResponseEntity getAccountImage() throws Exception {
		ProfileDto profileDto = profilesService.getAccountDetails();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileDto);
	}
}
