package com.direct.app.ui.controller;

import com.direct.app.service.NetworkService;
import com.direct.app.io.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/connections")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ConnectionsController {

	@Autowired
    private NetworkService networkService;

	@GetMapping
	public ResponseEntity accessNetwork() throws Exception {

		Set<ProfileDto> responseModels = networkService.retrieveNetwork();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseModels);
	}


	@DeleteMapping("/remove/{connectionId}")
	public ResponseEntity removeConnection(@PathVariable long connectionId) throws Exception {
		networkService.removeConnection(connectionId);

		return ResponseEntity
						.status(HttpStatus.OK)
						.build();
	}
}
