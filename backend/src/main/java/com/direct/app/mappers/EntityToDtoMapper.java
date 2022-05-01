package com.direct.app.mappers;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.shared.dto.BaseDTO;

import java.util.List;

public interface EntityToDtoMapper {
	public BaseDTO mapToDTO(BaseEntity entity);
	public List<? extends BaseDTO> mapToDTOs(List<? extends BaseEntity> entities);
}
