package com.company.app.service;

import com.company.app.shared.dto.RequestDto;
import com.company.app.ui.models.response.ProfileResponseModel;
import com.company.app.ui.models.response.RequestsResponseModel;

import java.util.List;
import java.util.Set;

public interface IConnectionRequestService {
	
	public RequestDto createConnectionRequest(long receiverId) throws Exception;
	
	public boolean acceptConnectionRequest(long id);
	
	public void rejectConnectionRequest(long id);

	public List<RequestsResponseModel> retrieveConnectionsRequests() throws Exception;

	public Set<ProfileResponseModel> retrieveNetwork() throws Exception;
}
