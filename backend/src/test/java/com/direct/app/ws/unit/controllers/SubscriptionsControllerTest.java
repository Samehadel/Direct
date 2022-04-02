package com.direct.app.ws.unit.controllers;

import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.KeywordDto;
import com.direct.app.ui.controller.SubscriptionsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
        KeywordDto keywordDto = new KeywordDto(1, 1L);

        // Mocking Stage 1
        when(subscriptionService.createSubscription(anyLong(), anyInt())).thenReturn(true);

        ResponseEntity responseEntity = subscriptionsController.subscribe(keywordDto);

        // Assertion Stage 1
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


        // Mocking Stage 2
        when(subscriptionService.createSubscription(anyLong(), anyInt())).thenReturn(false);

        responseEntity = subscriptionsController.subscribe(keywordDto);

        // Assertion Stage 2
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void accessSubscriptions_happy_path() throws Exception {

        // Mocking Stage
        when(subscriptionService.getSubscriptions(anyLong())).thenReturn(new ArrayList<KeywordDto>());
        when(userService.getCurrentUserId()).thenReturn(1L);

        ResponseEntity responseEntity = subscriptionsController.accessSubscriptions();

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void removeSubscribedKeywords_happy_path() throws Exception {
        ResponseEntity responseEntity = subscriptionsController.accessSubscriptions();

        // Assertion Stage
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
