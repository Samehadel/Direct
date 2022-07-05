package com.direct.app.enumerations;

import com.direct.app.mappers.EntityDTOMapper;
import com.direct.app.mappers.impl.*;

public enum EntityDTOMapperType {

	KEYWORD_MAPPER {
		@Override
		public EntityDTOMapper getMapper() {
			return new KeywordEntityToDtoMapper();
		}
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
			return new SubscriptionEntityToDtoMapper();
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

	public abstract EntityDTOMapper getMapper();
}
