package com.direct.app.service.util.publication_service_utils;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.SubscriptionEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.io.dto.PublicationDto;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.direct.app.enumerations.EntityDTOMapperType.PUBLICATION_MAPPER;

public class PublishPostsUtilImplementation {

	@Autowired
	private PublicationsRepository publicationRepo;

	@Autowired
	private ConnectionRepository connectionRepo;

	@Autowired
	private UserService userService;

	private UserEntity sender = new UserEntity();
	private List<ConnectionEntity> userConnections;
	private List<Integer> keywords;
	private EntityDTOMapper entityDTOMapper;
	private PublicationDto publicationDto;

	@PostConstruct
	public void initialize() {
		entityDTOMapper = EntityDTOMapperFactory.getEntityDTOMapper(PUBLICATION_MAPPER);
	}


	protected void prepareEntities(PublicationDto publicationDto) throws Exception {
		Long userId = publicationDto.getSenderId();
		this.publicationDto = publicationDto;
		userConnections = connectionRepo.findByUserId(userId);
		keywords = publicationDto.getKeywords();
		sender = userService.retrieveUserById(userId);
	}


	protected List<PublicationEntity> buildPublications() {

		Set<UserEntity> receivers =
				userConnections
						.stream()
						.map(conn -> getReceiverFromConnection(conn))
						.map(UserEntity::getSubscriptions)
						.flatMap(List::stream)
						.filter(this::keywordsContainSubscription)
						.map(SubscriptionEntity::getUser)
						.collect(Collectors.toSet());

		return buildPublications(receivers);
	}

	private UserEntity getReceiverFromConnection(ConnectionEntity conn) {
		UserEntity receiver = conn.getFirstUser();
		if (receiver.getId().equals(publicationDto.getSenderId()))
			receiver = conn.getSecondUser();

		return receiver;
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
		PublicationEntity publication = (PublicationEntity) entityDTOMapper.mapDtoToEntity(publicationDto);

		publication.setReceiver(receiver);
		publication.setSender(sender);

		receiver.addReceivedPublication(publication);
		receiver.addSentPublication(publication);

		return publication;
	}


	protected void savePublications(List<PublicationEntity> publications) {
		for (PublicationEntity publication : publications)
			publicationRepo.save(publication);

	}
}
