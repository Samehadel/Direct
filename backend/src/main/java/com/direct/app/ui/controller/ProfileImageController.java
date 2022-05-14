package com.direct.app.ui.controller;

import com.direct.app.service.ProfileImageService;
import com.direct.app.shared.dto.ProfileImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/profile")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfileImageController {
	@Autowired
	private ProfileImageService profileImageService;

	@PostMapping(value = "/image", produces = APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity setProfileImage(@RequestPart("imageFile") MultipartFile imageFile) throws Exception {
		String imageUrl = profileImageService.setProfileImage(imageFile);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(imageUrl);
	}

	@GetMapping("/image")
	public ResponseEntity getProfileImage() throws Exception {
		ProfileImageDTO profileImageDTO = profileImageService.getProfileImage();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileImageDTO);
	}
}
