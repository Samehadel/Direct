package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.SubscriptionRepository;
import com.direct.app.service.KeywordService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.shared.EntityDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.direct.app.enumerations.EntityDTOMapperType.SUBSCRIPTION_MAPPER;
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

    @Autowired
    private EntityDTOConverter converter;

    @Override
    public void subscribeToKeyword(Integer keywordId) throws Exception {
        UserEntity user = userService.getCurrentUserEntity();

        KeywordEntity keyword = keywordService.getKeywordById(keywordId)
                .orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, KWRD$0001, keywordId));

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUser(user);
        subscription.setKeyword(keyword);

        user.addSubscription(subscription);
        keyword.addSubscription(subscription);

        subscriptionRepo.save(subscription);
    }

    @Override
    public List<SubscriptionDTO> getCurrentUserSubscriptions() {
        Long userId = userService.getCurrentUserId();

        List<SubscriptionEntity> subscriptions = subscriptionRepo.findAllByUserId(userId);
        List<SubscriptionDTO> subscriptionsDto = (List<SubscriptionDTO>) converter.mapToDTOs(subscriptions);

        return subscriptionsDto;
    }

    @Override
    public void unsubscribeToKeyword(Integer keywordId) throws Exception {
        Long userId = userService.getCurrentUserId();

        subscriptionRepo.deleteSubscribedKeyword(userId, keywordId);
    }

}
