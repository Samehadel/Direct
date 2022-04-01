package com.direct.app.service;

import com.direct.app.shared.dto.ConnectionRequestDto;

import java.util.List;

public interface ConnectionRequestService {
	
	public ConnectionRequestDto createConnectionRequest(ConnectionRequestDto connectionRequestDto) throws Exception;
	
	public boolean acceptConnectionRequest(long id);
	
	public void rejectConnectionRequest(long id);

	public List<ConnectionRequestDto> retrieveConnectionsRequests() throws Exception;

}
