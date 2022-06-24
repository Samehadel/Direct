package com.direct.app.service;

import java.util.List;

import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.io.dto.KeywordDto;

public interface KeywordService {

	public boolean addKeyword(String keyword);
	
	public KeywordEntity getKeywordById(int id);
	
	public List<KeywordDto> getAllExistingKeywords() throws Exception;

	public List<KeywordDto> getKeywordsWithSubscriptions() throws Exception;

}
