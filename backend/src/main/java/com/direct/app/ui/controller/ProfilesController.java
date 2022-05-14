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

	@Autowired
	private ProfilesService profilesService;

	@GetMapping
	public ResponseEntity findSimilarProfilesForUser() throws Exception {
		Set<ProfileDto> similarUsers = profilesService.retrieveSimilarUsers();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(similarUsers);
	}
}
