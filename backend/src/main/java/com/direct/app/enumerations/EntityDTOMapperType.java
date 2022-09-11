package com.direct.app.enumerations;

import com.direct.app.io.dto.*;
import com.direct.app.io.entities.*;
import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.mappers.impl.*;

public enum EntityDTOMapperType {

	KEYWORD_MAPPER {
		@Override
		public EntityDTOMapper getMapper() { return new KeywordEntityDTOMapper(); }
	}, PUBLICATION_MAPPER {
		@Override
		public EntityDTOMapper getMapper() {
			return new PublicationEntityDTOMapper();
		}
	}, REQUEST_MAPPER {
		@Override
		public EntityDTOMapper getMapper() {
			return new RequestEntityDTOMapper();
		}
	}, SUBSCRIPTION_MAPPER {
		@Override
		public EntityDTOMapper getMapper() {
			return new SubscriptionEntityDTOMapper();
		}
	}, USER_MAPPER {
		@Override
		public EntityDTOMapper getMapper() {
			return new UserEntityDTOMapper();
		}
	}, USER_IMAGE_MAPPER {
		@Override
		public EntityDTOMapper getMapper() {
			return new UserImageEntityDTOMapper();
		}
	};

	protected abstract EntityDTOMapper getMapper();

	public static EntityDTOMapper getMapper(BaseEntity entity){
		if(entity instanceof KeywordEntity){
			return KEYWORD_MAPPER.getMapper();
		} else if(entity instanceof PublicationEntity){
			return PUBLICATION_MAPPER.getMapper();
		} else if(entity instanceof RequestEntity){
			return REQUEST_MAPPER.getMapper();
		} else if(entity instanceof SubscriptionEntity){
			return SUBSCRIPTION_MAPPER.getMapper();
		} else if(entity instanceof UserImageEntity){
			return USER_IMAGE_MAPPER.getMapper();
		} else if(entity instanceof UserEntity){
			return USER_MAPPER.getMapper();
		}else {
			return null;
		}
	}

	public static EntityDTOMapper getMapper(BaseDTO dto){
		if(dto instanceof KeywordDto){
			return KEYWORD_MAPPER.getMapper();
		} else if(dto instanceof PublicationDto){
			return PUBLICATION_MAPPER.getMapper();
		} else if(dto instanceof ConnectionRequestDto){
			return REQUEST_MAPPER.getMapper();
		} else if(dto instanceof SubscriptionDTO){
			return SUBSCRIPTION_MAPPER.getMapper();
		} else if(dto instanceof ProfileImageDTO){
			return USER_IMAGE_MAPPER.getMapper();
		} else if(dto instanceof UserDto){
			return USER_MAPPER.getMapper();
		}else {
			return null;
		}
	}
}
