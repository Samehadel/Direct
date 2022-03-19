package com.direct.app.ws.unit.controllers;

import com.direct.app.service.IConnectionRequestService;
import com.direct.app.service.IPublicationsService;
import com.direct.app.ui.controller.PublicationsController;
import com.direct.app.ui.controller.RequestsController;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestsControllerTest {
    @Mock
    private IConnectionRequestService requestService;

    @InjectMocks
    private RequestsController requestsController;

    @Before
    public void init(){
        this.requestsController = new RequestsController();

        MockitoAnnotations.initMocks(this);
    }
}
