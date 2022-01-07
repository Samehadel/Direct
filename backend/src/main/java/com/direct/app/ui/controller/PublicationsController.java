package com.direct.app.ui.controller;

import com.direct.app.service.IPublicationsService;
import com.direct.app.ui.models.request.PublicationRequestModel;
import com.direct.app.ui.models.response.PublicationResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class PublicationsController {

    @Autowired
    IPublicationsService publicationService;


    @PostMapping("/publish")
    public ResponseEntity publishJobPost(@RequestBody PublicationRequestModel publication) throws Exception {

        publicationService.publish(publication);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping
    public ResponseEntity accessInboxPublications() throws Exception {
        //Use of service
        List<PublicationResponseModel> publications = publicationService.retrievePublications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publications);
    }

    @PutMapping("/status/read/{publicationId}")
    public ResponseEntity markPublicationAsRead(@PathVariable long publicationId) {

        // Service Call
        boolean check = publicationService.markPublicationAsRead(publicationId, true);

        return check ? ResponseEntity
                            .status(HttpStatus.OK)
                            .build() :
                       ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build();
    }
    @PutMapping("/status/unread/{publicationId}")
    public ResponseEntity markPublicationAsUnRead(@PathVariable long publicationId) {

        // Service Call
        boolean check = publicationService.markPublicationAsRead(publicationId, false);

        return check ? ResponseEntity
                            .status(HttpStatus.OK)
                            .build() :
                       ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build();
    }
}
