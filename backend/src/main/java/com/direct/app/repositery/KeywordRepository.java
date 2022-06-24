package com.direct.app.repositery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.direct.app.io.entities.KeywordEntity;

import java.util.List;

@Repository
public interface KeywordRepository extends CrudRepository<KeywordEntity, Integer> {
	public KeywordEntity findById(int id);
	public List<KeywordEntity> findAll();
}
