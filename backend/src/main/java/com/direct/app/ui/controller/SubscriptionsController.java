package com.direct.app.ui.controller;

import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class SubscriptionsController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity subscribe(@RequestParam(value = "keyword_id") Integer keywordId) throws Exception {

        subscriptionService.subscribeToKeyword(keywordId);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/")
    public ResponseEntity accessSubscriptions() throws Exception {

        List<SubscriptionDTO> subscriptions = subscriptionService.getCurrentUserSubscriptions();


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subscriptions);
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity unsubscribedKeyword(@RequestParam(value = "keyword_id") Integer keywordId) throws Exception {
        subscriptionService.unsubscribeToKeyword(keywordId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}