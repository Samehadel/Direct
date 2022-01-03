package com.direct.app.ui.controller;

import com.direct.app.service.INetworkService;
import com.direct.app.ui.models.response.ProfileResponseModel;
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
    private INetworkService networkService;

	/**
	 	* This endpoint used by users to access their network
	 */
	@GetMapping
	public ResponseEntity accessNetwork() throws Exception {

		Set<ProfileResponseModel> responseModels = networkService.retrieveNetwork();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseModels);
	}


    /**
     * This endpoint is used by users to remove a connection in their network
     * @param connectionId is the connection id
     */
	@DeleteMapping("/remove/{connectionId}")
	public ResponseEntity removeConnection(@PathVariable long connectionId){
		boolean check = networkService.removeConnection(connectionId);

		return check ? ResponseEntity
						.status(HttpStatus.OK)
						.build() : ResponseEntity
									.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.build();
	}
}
