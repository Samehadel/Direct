package com.direct.app.mappers;

import com.direct.app.io.dto.BaseDTO;
import com.direct.app.io.entities.BaseEntity;

import java.util.List;

public interface EntityDTOMapper {
	public BaseEntity mapDtoToEntity(BaseDTO dto);
	public List<? extends BaseEntity> mapDTOsToEntities(List<? extends BaseDTO> dtos);

	public BaseDTO mapEntityToDTO(BaseEntity entity);
	public List<? extends BaseDTO> mapEntitiesToDTOs(List<? extends BaseEntity> entities);
}
