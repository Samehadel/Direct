package com.direct.app.service.implementation;

import com.direct.app.io.dto.KeywordDto;
import com.direct.app.io.dto.SubscriptionDTO;
import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.mappers.EntityToDtoMapper;
import com.direct.app.mappers.impl.KeywordEntityToDtoMapper;
import com.direct.app.repositery.KeywordRepository;
import com.direct.app.service.KeywordService;
import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KeywordServiceImplementation implements KeywordService {

	@Autowired
    private KeywordRepository keywordRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SubscriptionService subscriptionService;

	private EntityToDtoMapper entityToDtoMapper;

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
		entityToDtoMapper = new KeywordEntityToDtoMapper();
		List<KeywordEntity> keywords = keywordRepo.findAll();
		List<KeywordDto> keywordsDto = (List<KeywordDto>) entityToDtoMapper.mapToDTOs(keywords);
	
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