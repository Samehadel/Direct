package com.direct.app.service.util.publication_service_utils;

import com.direct.app.io.dto.PublicationDto;

public interface PostsPublisher {
	public int publish(PublicationDto publication) throws Exception;
}
