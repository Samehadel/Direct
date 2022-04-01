package com.direct.app.service;

import com.direct.app.shared.dto.PublicationDto;

import java.util.List;

public interface PublicationsService {

	public List<PublicationDto> retrievePublications() throws Exception;
	public void publish(PublicationDto publication) throws Exception;
	public boolean markPublicationAsRead (long publicationId, boolean isRead);
}
