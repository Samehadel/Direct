package com.direct.app.ui.controller;

import com.direct.app.service.ConnectionRequestService;
import com.direct.app.shared.dto.ConnectionRequestDto;
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
    private ConnectionRequestService requestService;

    @PostMapping("/send")
    public ResponseEntity sendConnectionRequest(@RequestBody ConnectionRequestDto connectionRequestDto) throws Exception {


        ConnectionRequestDto serviceBackDto = requestService.createConnectionRequest(connectionRequestDto);

        return serviceBackDto.getId() == 0 ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() :
                ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity accessUserReceivedConnectionRequests() throws Exception {

        List<ConnectionRequestDto> requests = requestService.retrieveConnectionRequests();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requests);
    }

    @PutMapping("/accept")
    public ResponseEntity acceptConnectionRequest(@RequestParam("connection_request_id") Long requestId) throws Exception {

        boolean check = requestService.acceptConnectionRequest(requestId);

        return check ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/reject")
    public ResponseEntity rejectConnectionRequest(@RequestParam("connection_request_id") Long requestId) throws Exception {
        requestService.rejectConnectionRequest(requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
