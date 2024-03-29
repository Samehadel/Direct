package com.direct.app.ui.controller;

import com.direct.app.io.dto.ProfileImageDTO;
import com.direct.app.service.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/profile")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ProfileImageController {
	@Autowired
	private ProfileImageService profileImageService;

	@PostMapping(value = "/image", produces = APPLICATION_JSON_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity setProfileImage(@RequestPart("image_file") MultipartFile imageFile) throws Exception {
		String imageUrl = profileImageService.setProfileImage(imageFile);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(imageUrl);
	}

	@GetMapping("/image")
	public ResponseEntity getProfileImage() throws Exception {
		ProfileImageDTO profileImageDTO = ofNullable(profileImageService.getProfileImage()).orElse(new ProfileImageDTO());

		MediaType mediaType = profileImageDTO.getImageFormat() == null ? null : MediaType.parseMediaType(profileImageDTO.getImageFormat());

		return ResponseEntity
				.ok()
				.contentType(mediaType)
				.body(profileImageDTO.getImageData());
	}
}
