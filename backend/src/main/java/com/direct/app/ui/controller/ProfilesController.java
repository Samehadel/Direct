package com.direct.app.ui.controller;

import com.direct.app.service.ProfilesService;
import com.direct.app.io.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
