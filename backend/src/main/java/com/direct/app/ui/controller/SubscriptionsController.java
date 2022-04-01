package com.direct.app.ui.controller;

import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.KeywordDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity subscribe(@RequestBody KeywordDto keywordDto) throws Exception {

        Long userId = userService.retrieveUserId();

        boolean check = subscriptionService.createSubscription(userId, keywordDto.getKeywordId());

        return check ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("/")
    public ResponseEntity accessSubscriptions() throws Exception {

        // Extract user's info from ContextHolder and get user's id from DB
        long userId = userService.retrieveUserId();

        List<KeywordDto> keywordsSubscriptions = new ArrayList<>();
        List<KeywordDto> subscriptionsDto = subscriptionService.getSubscriptions(userId);

        for (KeywordDto dto : subscriptionsDto) {
            KeywordDto responseDto = new KeywordDto();

            // Copy values from DTO to response model
            BeanUtils.copyProperties(dto, responseDto);

            keywordsSubscriptions.add(responseDto);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(keywordsSubscriptions);
    }

    @DeleteMapping("/drop/{subscriptionId}")
    public ResponseEntity removeSubscription(@PathVariable long subscriptionId) {
        subscriptionService.dropSubscription(subscriptionId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
    @DeleteMapping("/drop/keyword/{keywordId}")
    public ResponseEntity removeSubscribedKeyword(@PathVariable int keywordId) throws Exception {
        subscriptionService.dropSubscription(keywordId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
