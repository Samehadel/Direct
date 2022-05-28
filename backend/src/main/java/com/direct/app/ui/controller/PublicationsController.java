package com.direct.app.ui.controller;

import com.direct.app.service.PublicationsService;
import com.direct.app.io.dto.PublicationDto;
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
    PublicationsService publicationService;


    @PostMapping("/publish")
    public ResponseEntity publishJobPost(@RequestBody PublicationDto publication) throws Exception {

        publicationService.publish(publication);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping
    public ResponseEntity accessInboxPublications() throws Exception {
        List<PublicationDto> publications = publicationService.retrievePublications();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publications);
    }

    @PutMapping("/status/read")
    public ResponseEntity markPublicationAsRead(@RequestParam("id") long publicationId) throws Exception {
        publicationService.markPublicationAsRead(publicationId);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
    }

    @PutMapping("/status/unread")
    public ResponseEntity markPublicationAsUnRead(@RequestParam("id") long publicationId) throws Exception {
        publicationService.markPublicationAsUnRead(publicationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
