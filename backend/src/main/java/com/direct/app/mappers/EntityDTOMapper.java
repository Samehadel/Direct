package com.direct.app.mappers;


import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.entities.BaseEntity;

import java.util.List;

public interface EntityDTOMapper {
	BaseEntity mapDtoToEntity(BaseDTO dto);
	List<? extends BaseEntity> mapDTOsToEntities(List<? extends BaseDTO> dtos);

	BaseDTO mapEntityToDTO(BaseEntity entity);
	List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities);
}
