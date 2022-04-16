package com.direct.app.service;

import com.direct.app.shared.dto.ConnectionRequestDto;

import java.util.List;

public interface ConnectionRequestService {
	
	public Long sendConnectionRequest(ConnectionRequestDto connectionRequestDto) throws Exception;
	
	public boolean acceptConnectionRequest(long id) throws Exception;
	
	public boolean rejectConnectionRequest(long connectionRequestId) throws Exception;

	public List<ConnectionRequestDto> retrieveConnectionRequests() throws Exception;

}
