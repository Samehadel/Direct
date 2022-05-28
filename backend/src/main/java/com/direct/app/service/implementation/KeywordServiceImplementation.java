package com.direct.app.service.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.direct.app.service.SubscriptionService;
import com.direct.app.service.UserService;
import com.direct.app.repositery.KeywordRepository;
import com.direct.app.io.dto.KeywordDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.service.KeywordService;

@Service
public class KeywordServiceImplementation implements KeywordService {

	@Autowired
    KeywordRepository keywordRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SubscriptionService subscriptionService;
	
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
	public KeywordEntity getKeywordById(int id) {
		return keywordRepo.findById(id);
	}

	@Override
	public List<KeywordDto> getKeywords() throws Exception{

		//List of keyword DTOs
		ArrayList<KeywordDto> keywordsDto = new ArrayList<KeywordDto>();
		KeywordDto dto = new KeywordDto();
		
		//Use Repository
		Iterable<KeywordEntity> keywords = keywordRepo.findAll();

		Iterator<KeywordEntity> itr = keywords.iterator();


		while(itr.hasNext()) {
			BeanUtils.copyProperties(itr.next(), dto);

			keywordsDto.add(dto);
			
			dto = new KeywordDto();
		}
	
		return keywordsDto;
	}

	@Override
	public List<KeywordDto> getKeywordsWithSubscriptions() throws Exception {
		long userId = userService.getCurrentUserId();
		List<KeywordDto> keywords = getKeywords();
		assignSubscriptions(keywords, userId);

		return keywords;
	}

	private List<KeywordDto> assignSubscriptions(List<KeywordDto>keywordDtos, long userId){
		// Another service call
		List<KeywordDto> subscriptions = subscriptionService.getSubscriptions(userId);

		for(KeywordDto dto: keywordDtos){
			for(KeywordDto sub: subscriptions){
				if(sub.getKeywordId() == dto.getKeywordId())
					dto.setSubscribed(true);
			}
		}

		return keywordDtos;
	}

}
