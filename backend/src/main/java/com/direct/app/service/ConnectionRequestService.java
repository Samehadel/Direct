package com.direct.app.service;

import com.direct.app.io.dto.ConnectionRequestDto;

import java.util.List;

public interface ConnectionRequestService {
	
	public Long sendConnectionRequest(Long receiverId) throws Exception;
	
	public boolean acceptConnectionRequest(long id) throws Exception;
	
	public boolean rejectConnectionRequest(long connectionRequestId) throws Exception;

	public List<ConnectionRequestDto> retrieveConnectionRequests() throws Exception;

}
