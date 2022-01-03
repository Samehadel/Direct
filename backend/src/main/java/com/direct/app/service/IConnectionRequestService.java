package com.direct.app.service;

import com.direct.app.shared.dto.RequestDto;
import com.direct.app.ui.models.response.RequestsResponseModel;

import java.util.List;

public interface IConnectionRequestService {
	
	public RequestDto createConnectionRequest(long receiverId) throws Exception;
	
	public boolean acceptConnectionRequest(long id);
	
	public void rejectConnectionRequest(long id);

	public List<RequestsResponseModel> retrieveConnectionsRequests() throws Exception;

}
