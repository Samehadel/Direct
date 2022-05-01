package com.direct.app.mappers;

import com.direct.app.io.entities.BaseEntity;
import com.direct.app.shared.dto.BaseDTO;

import java.util.List;

public interface DtoToEntityMapper {
	public BaseEntity mapToEntity(BaseDTO dto);
	public List<? extends BaseEntity> mapToEntities(List<? extends BaseDTO> dtos);
}
