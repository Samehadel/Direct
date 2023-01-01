package com.direct.app.service.util.publication_service_utils;

import com.direct.app.io.dto.PublicationDto;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.service.UserService;
import com.direct.app.shared.EntityDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class PostsPublisherImpl implements PostsPublisher{

    @Autowired
    private PublicationsRepository publicationRepo;

    @Autowired
    private ConnectionRepository connectionRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityDTOConverter converter;

    private UserEntity sender = new UserEntity();
    private List<ConnectionEntity> userConnections;
    private List<Integer> keywords;
    private PublicationDto publicationDto;


    public int publish(PublicationDto publicationDto) throws Exception {

        prepareEntities(publicationDto);

        List<PublicationEntity> publications = buildPublications();

        return savePublicationsToDB(publications);
    }

    private void prepareEntities(PublicationDto publicationDto) throws Exception {
        Long userId = publicationDto.getSenderId();
        this.publicationDto = publicationDto;
        userConnections = connectionRepo.findByUserId(userId);
        keywords = publicationDto.getKeywords();
        sender = userService.retrieveUserById(userId);
    }

    private List<PublicationEntity> buildPublications() {
        Set<UserEntity> receivers = filterUserConnectionsBasedOnSubscriptions();

        return buildPublications(receivers);
    }

    private Set<UserEntity> filterUserConnectionsBasedOnSubscriptions() {
        return userConnections
                    .stream()
                    .map(conn -> getReceiverFromConnection(conn))
                    .map(UserEntity::getSubscriptions)
                    .flatMap(List::stream)
                    .filter(this::keywordsContainSubscription)
                    .map(SubscriptionEntity::getUser)
                    .collect(Collectors.toSet());
    }

    private UserEntity getReceiverFromConnection(ConnectionEntity conn) {
        UserEntity receiver = conn.getFirstUser();
        if (receiverIsSecondUserInConnecttion(receiver))
            receiver = conn.getSecondUser();

        return receiver;
    }

    private boolean receiverIsSecondUserInConnecttion(UserEntity receiver){
        return receiver.getId().equals(publicationDto.getSenderId());
    }

    private boolean keywordsContainSubscription(SubscriptionEntity subscription) {
        return keywords.contains(subscription.getKeyword().getId());
    }

    private List<PublicationEntity> buildPublications(Set<UserEntity> receivers) {
        return receivers
                .stream()
                .map(this::buildSinglePublication)
                .collect(Collectors.toList());
    }

    private PublicationEntity buildSinglePublication(UserEntity receiver) {
        PublicationEntity publication = (PublicationEntity) converter.mapToEntity(publicationDto);

        publication.setReceiver(receiver);
        publication.setSender(sender);

        receiver.addReceivedPublication(publication);
        receiver.addSentPublication(publication);

        return publication;
    }

    private int savePublicationsToDB(List<PublicationEntity> publications) {
        int successfulPublications = 0;
        for (PublicationEntity publication : publications) {
            try{
                publicationRepo.save(publication);
                successfulPublications ++;
            }catch (Exception e){

            }
        }
        return successfulPublications;
    }
}
