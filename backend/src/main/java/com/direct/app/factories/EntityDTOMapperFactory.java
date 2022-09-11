package com.direct.app.factories;

import com.direct.app.enumerations.EntityDTOMapperType;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.mappers.EntityDTOMapper;

public interface EntityDTOMapperFactory {

	static EntityDTOMapper getEntityDTOMapper(BaseEntity entity){
		return EntityDTOMapperType.getMapper(entity);
	}

	static EntityDTOMapper getEntityDTOMapper(BaseDTO dto){
		return EntityDTOMapperType.getMapper(dto);
	}
}
