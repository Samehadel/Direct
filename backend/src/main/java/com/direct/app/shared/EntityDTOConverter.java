package com.direct.app.shared;

import com.direct.app.factories.EntityDTOMapperFactory;
import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.entities.BaseEntity;
import com.direct.app.mappers.EntityDTOMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityDTOConverter {

	public BaseEntity mapToEntity(BaseDTO dto){
		EntityDTOMapper mapper = EntityDTOMapperFactory.getEntityDTOMapper(dto);
		return mapper.mapDtoToEntity(dto);
	}

	public BaseDTO mapToDTO(BaseEntity entity){
		EntityDTOMapper mapper = EntityDTOMapperFactory.getEntityDTOMapper(entity);
		return mapper.mapEntityToDTO(entity);
	}

	public List<? extends BaseEntity> mapToEntities(List<? extends BaseDTO> dtos){
		List<BaseEntity> entities = new ArrayList<>();
		dtos.forEach(dto -> {
			BaseEntity entity = mapToEntity(dto);
			entities.add(entity);
		});
		return entities;
	}

	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities){
		List<BaseDTO> dtos = new ArrayList<>();
		entities.forEach(entity -> {
			BaseDTO dto = mapToDTO(entity);
			dtos.add(dto);
		});
		return dtos;
	}

}
