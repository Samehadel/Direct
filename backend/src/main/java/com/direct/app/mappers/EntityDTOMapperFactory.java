package com.direct.app.mappers;


import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.dto.UserDTO;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.mappers.impl.UserEntityDTOMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EntityDTOMapperFactory {

	private EntityDTOMapperFactory (){

	}

	protected static EntityDTOMapper getEntityDTOMapper(BaseEntity entity){
		if(entity instanceof UserEntity)
			return UserEntityDTOMapper.getInstance(new BCryptPasswordEncoder());

		throw new AssertionError();
	}

	protected static EntityDTOMapper getEntityDTOMapper(BaseDTO dto){
		if(dto instanceof UserDTO)
			return UserEntityDTOMapper.getInstance(new BCryptPasswordEncoder());

		throw new AssertionError();
	}
}
