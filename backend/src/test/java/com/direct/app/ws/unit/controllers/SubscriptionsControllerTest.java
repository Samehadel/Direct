package com.direct.app.ws.unit.controllers;

import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.ui.controller.SubscriptionsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class SubscriptionsControllerTest {

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionsController subscriptionsController;

    @Mock
    private UserService userService;

    @Before
    public void init(){
        this.subscriptionsController = new SubscriptionsController();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void subscribe_happy_path_test() throws Exception {

        ResponseEntity responseEntity = subscriptionsController.subscribe(anyInt());

        // Assertion Stage 1
        Assert.assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void accessSubscriptions_happy_path() throws Exception {

        // Mocking Stage
        when(subscriptionService.getCurrentUserSubscriptions()).thenReturn(new ArrayList<SubscriptionDTO>());
        when(userService.getCurrentUserId()).thenReturn(1L);

        ResponseEntity responseEntity = subscriptionsController.accessSubscriptions();

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), OK);
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void removeSubscribedKeywords_happy_path() throws Exception {
        ResponseEntity responseEntity = subscriptionsController.accessSubscriptions();

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), OK);
    }
}
