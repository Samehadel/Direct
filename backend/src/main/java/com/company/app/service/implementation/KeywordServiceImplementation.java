package com.company.app.service.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.company.app.io.entities.UserEntity;
import com.company.app.service.ISubscriptionService;
import com.company.app.service.IUserService;
import com.company.app.shared.dto.SubscriptionDto;
import com.company.app.shared.dto.UserDto;
import io.swagger.annotations.Authorization;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.company.app.io.entities.KeywordEntity;
import com.company.app.repositery.KeywordRepository;
import com.company.app.service.IKeywordService;
import com.company.app.shared.dto.KeywordDto;

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
