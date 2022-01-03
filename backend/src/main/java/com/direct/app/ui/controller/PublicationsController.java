package com.direct.app.ui.controller;

import com.direct.app.service.IPublicationsService;
import com.direct.app.shared.dto.PublicationDto;
import com.direct.app.ui.models.request.PublicationRequestModel;
import com.direct.app.ui.models.response.PublicationResponseModel;
import org.springframework.beans.BeanUtils;
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
	public ResponseEntity publishJobPost(@RequestBody PublicationRequestModel publication){

		publicationService.publish(publication);

		return ResponseEntity
				.status(HttpStatus.OK)
				.build();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity accessInboxPublications(@PathVariable long userId){
		//Use of service
		List<PublicationResponseModel> publications = publicationService.retrievePublications(userId);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(publications);
	}
	
}
