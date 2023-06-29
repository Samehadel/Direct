package com.direct.app.io.entities;

import com.direct.app.service.implementation.UserServiceImplementation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
public class PrePersistUserEntityListener {

	private final Logger logger = LogManager.getLogger(UserServiceImplementation.class);
	@PrePersist
	public void doBeforePersist(UserEntity userEntity) {
		logger.info("PrePersist UserEntity" + userEntity);
		userEntity.setUserCode("dummy");
	}
}
