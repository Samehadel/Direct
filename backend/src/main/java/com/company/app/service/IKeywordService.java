package com.company.app.service;

import java.util.List;

import com.company.app.io.entities.KeywordEntity;
import com.company.app.shared.dto.KeywordDto;

public interface IKeywordService {

	public boolean addKeyword(String keyword);
	
	public KeywordEntity getKeywordById(int id);
	
	public List<KeywordDto> getKeywords() throws Exception;
	
}
