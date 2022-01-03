package com.direct.app.service;

import java.util.List;

import com.direct.app.io.entities.KeywordEntity;
import com.direct.app.shared.dto.KeywordDto;

public interface IKeywordService {

	public boolean addKeyword(String keyword);
	
	public KeywordEntity getKeywordById(int id);
	
	public List<KeywordDto> getKeywords() throws Exception;
	
}
