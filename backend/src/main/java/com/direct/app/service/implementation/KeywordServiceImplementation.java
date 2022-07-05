package com.direct.app.service.implementation;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.repositery.KeywordRepository;
import com.direct.app.service.KeywordService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.direct.app.enumerations.EntityDTOMapperType.KEYWORD_MAPPER;

@Service
public class KeywordServiceImplementation implements KeywordService {

	@Autowired
    private KeywordRepository keywordRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SubscriptionService subscriptionService;

	private EntityDTOMapper entityDtoMapper;

	@Override
	public boolean addKeyword(String keyword) {

		KeywordEntity keywordEntity = new KeywordEntity();

		keywordEntity.setDescription(keyword);
		
		//Save and check back object
		if(keywordRepo.save(keywordEntity) == null)
			return false;
		
		return true;
	}
	
	@Override
	public Optional<KeywordEntity> getKeywordById(Integer id) {
		return keywordRepo.findById(id);
	}

	@Override
	public List<KeywordDto> getAllExistingKeywords() throws Exception{
		entityDtoMapper = EntityDTOMapperFactory.getEntityDTOMapper(KEYWORD_MAPPER);
		List<KeywordEntity> keywords = keywordRepo.findAll();
		List<KeywordDto> keywordsDto = (List<KeywordDto>) entityDtoMapper.mapEntitiesToDTOs(keywords);
	
		return keywordsDto;
	}

	@Override
	public List<KeywordDto> getKeywordsWithSubscriptions() throws Exception {
		List<KeywordDto> allKeywords = getAllExistingKeywords();
		assignSubscriptionsToKeywords(allKeywords);

		return allKeywords;
	}

	private List<KeywordDto> assignSubscriptionsToKeywords(List<KeywordDto> keywordDtos) throws Exception {
		keywordDtos
				.stream()
				.map(this::assignSubscriptionToKeyword)
				.collect(Collectors.toList());

		return keywordDtos;
	}
	private KeywordDto assignSubscriptionToKeyword(KeywordDto keywordDto) {

		if(didUserSubscribedToKeyword(keywordDto.getId()))
			keywordDto.setSubscribed(true);
		else
			keywordDto.setSubscribed(false);

		return keywordDto;
	}

	private boolean didUserSubscribedToKeyword(Integer keywordId){
		List<SubscriptionDTO> subscriptions = subscriptionService.getCurrentUserSubscriptions();
		return 1 == subscriptions
						.stream()
						.map(SubscriptionDTO::getKeyword)
						.filter(keyword -> keyword.getId().equals(keywordId))
						.count();
	}
}