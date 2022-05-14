package com.direct.app.ui.controller;

import com.direct.app.service.ProfileImageService;
import com.direct.app.service.ProfilesService;
import com.direct.app.shared.dto.ProfileImageDTO;
import com.direct.app.shared.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfilesController {
	// TODO: Separation of concerns
	@Autowired
	private ProfilesService profilesService;

	@Autowired
	private ProfileImageService profileImageService;

	@GetMapping
	public ResponseEntity findSimilarProfilesForUser() throws Exception {
		Set<ProfileDto> similarUsers = profilesService.retrieveSimilarUsers();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(similarUsers);
	}

	@PostMapping(value = "/image", produces = APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity setProfileImage(@RequestPart MultipartFile imageFile) throws Exception {
		String imageUrl = profileImageService.setProfileImage(imageFile);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(imageUrl);
	}

	@GetMapping("/details")
	public ResponseEntity getAccountImage() throws Exception {
		ProfileImageDTO profileImageDTO = profileImageService.getProfileImage();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileImageDTO);
	}
}
