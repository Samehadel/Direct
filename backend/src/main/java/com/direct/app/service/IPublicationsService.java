package com.direct.app.service;

import com.direct.app.ui.models.request.PublicationRequestModel;
import com.direct.app.ui.models.response.PublicationResponseModel;

import java.util.List;

public interface IPublicationsService {

	public List<PublicationResponseModel> retrievePublications(long userId);
	public void publish(PublicationRequestModel publication) throws Exception;
	
}
