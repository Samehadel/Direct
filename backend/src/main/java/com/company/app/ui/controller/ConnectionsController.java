package com.company.app.ui.controller;

import com.company.app.service.IConnectionRequestService;
import com.company.app.shared.dto.RequestDto;
import com.company.app.ui.models.request.ConnectionRequestModel;
import com.company.app.ui.models.response.ProfileResponseModel;
import com.company.app.ui.models.response.RequestsResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/connections")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class ConnectionsController {

	@Autowired
	IConnectionRequestService requestService;

	@GetMapping
	public ResponseEntity accessNetwork() throws Exception {

		Set<ProfileResponseModel> responseModels = requestService.retrieveNetwork();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(responseModels);
	}

	/**
	 * This endpoint is used to send a connection request from one user to another
	 * @param connectionRequest is the connection request model to hold the sender id and the receiver id
	 * @return a connection response model that holds the request information e.g. id
	 */
	@PostMapping("/request")
	public ResponseEntity sendConnectionRequest(@RequestBody ConnectionRequestModel connectionRequest) throws Exception {

		// Use the service to create a connection request
		RequestDto serviceBackDto = requestService.createConnectionRequest(connectionRequest.getReceiverId());

		return serviceBackDto.getId() == 0 ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() :
				ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * This endpoint is used to access user's received connection requests
	 * @return a connection response model that holds the request information e.g. id
	 */
	@GetMapping("/requests")
	public ResponseEntity accessConnectionsRequest() throws Exception {

		List<RequestsResponseModel> requests = requestService.retrieveConnectionsRequests();

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(requests);
	}

	/**
	 * This endpoint is used by a user(receiver) to accept a connection request
	 * @param requestId is the connection request id
	 */
	@PutMapping("/requests/accept/{requestId}")
	public ResponseEntity acceptConnectionRequest(@PathVariable long requestId) {
		
		boolean check = requestService.acceptConnectionRequest(requestId);

		return check ? ResponseEntity.status(HttpStatus.OK).build() :
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * This endpoint is used by a user(receiver) to reject a connection request
	 * @param requestId is the connection request id
	 */
	@DeleteMapping("/requests/reject/{requestId}")
	public ResponseEntity rejectConnectionRequest(@PathVariable long requestId) {
		requestService.rejectConnectionRequest(requestId);

		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}
}
