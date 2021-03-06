package com.direct.app.ws.integration;

import com.direct.app.repositery.UserRepository;
import com.direct.app.ui.controller.SubscriptionsController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubscriptionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SubscriptionsController subscriptionsController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void test(){
        Assert.assertEquals(0, 0);
    }
}
