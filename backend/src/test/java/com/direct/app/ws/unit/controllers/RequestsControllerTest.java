package com.direct.app.ws.unit.controllers;

import com.direct.app.service.ConnectionRequestService;
import com.direct.app.ui.controller.RequestsController;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RequestsControllerTest {
    @Mock
    private ConnectionRequestService requestService;

    @InjectMocks
    private RequestsController requestsController;

    @Before
    public void init(){
        this.requestsController = new RequestsController();

        MockitoAnnotations.initMocks(this);
    }
}
