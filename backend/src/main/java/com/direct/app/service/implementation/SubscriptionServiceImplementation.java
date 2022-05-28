package com.direct.app.service.implementation;

import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.SubscriptionRepository;
import com.direct.app.service.KeywordService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImplementation implements SubscriptionService {

    @Autowired
    UserService userService;

    @Autowired
    KeywordService keywordService;

    @Autowired
    SubscriptionRepository subscriptionRepo;


    @Override
    public boolean createSubscription(Long userId, Integer keywordId) throws Exception {

        //Retrieve user
        UserEntity user = userService.retrieveUserById(userId);

        //Retrieve keyword
        KeywordEntity keyword = keywordService.getKeywordById(keywordId);


        //create subscription
        SubscriptionEntity subscription = new SubscriptionEntity();

        /*Assign Relationships*/

        //Assign relationship between subscription and user (SUBSCRIPTION SIDE)
        subscription.setUser(user);

        //Assign relationship between subscription and keyword (SUBSCRIPTION SIDE)
        subscription.setKeyword(keyword);

        //Assign relationship between user and subscription (USER SIDE)
        user.addSubscription(subscription);

        //Assign relationship between keyword and subscription (KEYWORD SIDE)
        keyword.addSubscription(subscription);

        //Save subscription and check for nullness
        if (subscriptionRepo.save(subscription) != null) return true;

        return false;
    }

    @Override
    public List<KeywordDto> getSubscriptions(Long userId) {

        List<KeywordDto> subscriptionsDto = new ArrayList<>();

        //Use repository
        List<SubscriptionEntity> subscriptions = subscriptionRepo.findAllByUserId(userId);

        for (SubscriptionEntity sub : subscriptions) {
            KeywordDto dto = new KeywordDto();

            //Copy data from entity to DTO
            dto.setSubscriptionId(sub.getId());
            dto.setKeywordId(sub.getKeyword().getId());
            dto.setKeywordDescription(sub.getKeyword().getDescription());

            subscriptionsDto.add(dto);
        }
        return subscriptionsDto;
    }

    @Override
    public void dropSubscription(long subscriptionId) {

        //Create new subscription to be deleted
        SubscriptionEntity subscription = new SubscriptionEntity();

        //Assign id to be dropped
        subscription.setId(subscriptionId);

        //Drop subscription
        subscriptionRepo.delete(subscription);

    }

    @Override
    public void dropSubscription(int keywordId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        long userId = userService.getCurrentUserId();

        subscriptionRepo.deleteSubscribedKeyword(userId, keywordId);
    }

}
