package com.company.app.ui.controller;

import com.company.app.service.ISubscriptionService;
import com.company.app.service.IUserService;
import com.company.app.shared.dto.SubscriptionDto;
import com.company.app.shared.dto.UserDto;
import com.company.app.ui.models.request.SubscriptionRequestModel;
import com.company.app.ui.models.response.SubscriptionResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(value = "http://localhost:3000", allowedHeaders = "*")
public class SubscriptionsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISubscriptionService subscriptionService;

    private Authentication auth;

    @PostMapping("/subscribe")
    public ResponseEntity subscribe(@RequestBody SubscriptionRequestModel subscriptionBody) throws Exception {

        // Extract user's info from ContextHolder and get user's id from DB
        auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        long userId = userService.retrieveUserId();

        boolean check = subscriptionService.createSubscription(userId, subscriptionBody.getKeywordId());

        return check ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("/")
    public ResponseEntity accessSubscriptions() throws Exception {

        // Extract user's info from ContextHolder and get user's id from DB
        auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        long userId = userService.retrieveUserId();

        List<SubscriptionResponseModel> subscriptions = new ArrayList<>();
        List<SubscriptionDto> subscriptionsDto = subscriptionService.getSubscriptions(userId);

        for (SubscriptionDto dto : subscriptionsDto) {
            SubscriptionResponseModel responseModel = new SubscriptionResponseModel();

            // Copy values from DTO to response model
            BeanUtils.copyProperties(dto, responseModel);

            subscriptions.add(responseModel);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subscriptions);
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
