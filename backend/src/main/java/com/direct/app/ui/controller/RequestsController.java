package com.direct.app.ui.controller;

import com.direct.app.service.IConnectionRequestService;
import com.direct.app.shared.dto.RequestDto;
import com.direct.app.ui.models.request.ConnectionRequestModel;
import com.direct.app.ui.models.response.RequestsResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class RequestsController {

    @Autowired
    private IConnectionRequestService requestService;

    /**
     * This endpoint is used to send a connection request from one user to another
     * @param connectionRequest is the connection request model to hold the sender id and the receiver id
     * @return a connection response model that holds the request information e.g. id
     */
    @PostMapping("/send")
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
    @GetMapping
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
    @PutMapping("/accept/{requestId}")
    public ResponseEntity acceptConnectionRequest(@PathVariable long requestId) {

        boolean check = requestService.acceptConnectionRequest(requestId);

        return check ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * This endpoint is used by a user(receiver) to reject a connection request
     * @param requestId is the connection request id
     */
    @DeleteMapping("/reject/{requestId}")
    public ResponseEntity rejectConnectionRequest(@PathVariable long requestId) {
        requestService.rejectConnectionRequest(requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}