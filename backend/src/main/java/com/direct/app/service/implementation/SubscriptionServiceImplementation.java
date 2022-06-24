package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.mappers.impl.SubscriptionEntityToDtoMapper;
import com.direct.app.repositery.SubscriptionRepository;
import com.direct.app.service.KeywordService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.direct.app.exceptions.ErrorCode.KWRD$0001;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class SubscriptionServiceImplementation implements SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    private EntityToDtoMapper entityToDtoMapper;

    @Override
    public void subscribeToKeyword(Integer keywordId) throws Exception {
        UserEntity user = userService.getCurrentUserEntity();

        //Retrieve keyword
        KeywordEntity keyword = keywordService.getKeywordById(keywordId)
                .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, KWRD$0001, keywordId));


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
        subscriptionRepo.save(subscription);
    }

    @Override
    public List<SubscriptionDTO> getCurrentUserSubscriptions() {
        Long userId = userService.getCurrentUserId();
        entityToDtoMapper = new SubscriptionEntityToDtoMapper();

        List<SubscriptionEntity> subscriptions = subscriptionRepo.findAllByUserId(userId);
        List<SubscriptionDTO> subscriptionsDto = (List<SubscriptionDTO>) entityToDtoMapper.mapToDTOs(subscriptions);

        return subscriptionsDto;
    }

    @Override
    public void unsubscribeToKeyword(Integer keywordId) throws Exception {
        Long userId = userService.getCurrentUserId();

        subscriptionRepo.deleteSubscribedKeyword(userId, keywordId);
    }

}
