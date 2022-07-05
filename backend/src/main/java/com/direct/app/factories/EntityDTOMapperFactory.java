package com.direct.app.factories;

import com.direct.app.enumerations.EntityDTOMapperType;
import com.direct.app.mappers.EntityDTOMapper;

public class EntityDTOMapperFactory {

	public static EntityDTOMapper getEntityDTOMapper(EntityDTOMapperType mapperType){
		return mapperType.getMapper();
	}
}
