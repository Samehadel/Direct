package com.direct.app.ws.unit.controllers;

import com.direct.app.service.PublicationsService;
import com.direct.app.ui.controller.PublicationsController;
import com.direct.app.shared.dto.PublicationDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PublicationsControllerTest {

    @Mock
    private PublicationsService publicationsService;

    @InjectMocks
    private PublicationsController publicationsController;

    @Before
    public void init(){
        this.publicationsController = new PublicationsController();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void publishJobPost_happy_path_test() throws Exception {

        ResponseEntity responseEntity = publicationsController.publishJobPost(new PublicationDto());

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
    @Test
    public void accessInboxPublications_happy_path_test() throws Exception {

        ResponseEntity responseEntity = publicationsController.accessInboxPublications();

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
