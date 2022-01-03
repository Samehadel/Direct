package com.direct.app.service.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.direct.app.service.ISubscriptionService;
import com.direct.app.service.IUserService;
import com.direct.app.shared.dto.SubscriptionDto;
import com.direct.app.repositery.KeywordRepository;
import com.direct.app.shared.dto.KeywordDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.service.IKeywordService;

@Service
public class KeywordServiceImplementation implements IKeywordService {

	@Autowired
    KeywordRepository keywordRepo;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private ISubscriptionService subscriptionService;
	
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

		long userId = userService.retrieveUserId();

		//List of keyword DTOs
		ArrayList<KeywordDto> keywordsDto = new ArrayList<KeywordDto>();
		KeywordDto dto = new KeywordDto();
		
		//Use Repository
		Iterable<KeywordEntity> keywords = keywordRepo.findAll();
		
		// Another service call
		List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptions(userId);

		Iterator<KeywordEntity> itr = keywords.iterator();
		
		while(itr.hasNext()) {
			BeanUtils.copyProperties(itr.next(), dto);
			for(SubscriptionDto sub: subscriptions){
				if(sub.getKeywordId() == dto.getId())
					dto.setSubscribed(true);
			}
			keywordsDto.add(dto);
			
			dto = new KeywordDto();
		}
	
		return keywordsDto;
	}

}
