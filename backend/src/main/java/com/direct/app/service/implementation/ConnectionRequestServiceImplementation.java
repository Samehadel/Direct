package com.direct.app.service.implementation;

import com.direct.app.exceptions.ErrorCode;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.service.ConnectionRequestService;
import com.direct.app.service.UserService;
import com.direct.app.shared.dto.ConnectionRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionRequestServiceImplementation implements ConnectionRequestService {

	@Autowired
	UserService userService;
	
	@Autowired
	RequestRepository requestRepo; 
	
	@Autowired
	ConnectionRepository connectionRepo;
	
	@Override
	public ConnectionRequestDto createConnectionRequest(ConnectionRequestDto connectionRequestDto) throws Exception {

		validateSender(connectionRequestDto.getSenderId());
		RequestEntity newRequest = new RequestEntity();

		// Retrieve the two users from database
		UserEntity sender = userService.retrieveUser(connectionRequestDto.getSenderId());
		UserEntity receiver = userService.retrieveUser(connectionRequestDto.getReceiverId());

		assignConnectionRequestSender(sender, newRequest);
		assignConnectionRequestReceiver(receiver, newRequest);

		// Save the request
		requestRepo.save(newRequest);
		
		// Copy the properties to DTO
		connectionRequestDto.setId(newRequest.getId());
		connectionRequestDto.setSenderId(sender.getId());
		
		return connectionRequestDto;
	}

	public void validateSender(Long senderId) throws Exception{
		// Get username from security context
		Long currentUserId = userService.retrieveUserId();

		if(currentUserId != senderId)
			throw  new RuntimeBusinessException(HttpStatus.NOT_ACCEPTABLE, ErrorCode.U$0003, senderId);

	}

	private void assignConnectionRequestSender(UserEntity sender, RequestEntity newRequest){
		sender.addSentRequest(newRequest);
		newRequest.setSender(sender);
	}

	private void assignConnectionRequestReceiver(UserEntity receiver, RequestEntity newRequest){
		receiver.addReceivedRequest(newRequest);
		newRequest.setReceiver(receiver);
	}

	@Override
	public boolean acceptConnectionRequest(long id) {
		
		try {
		//Prepare connection object
		ConnectionEntity connection = new ConnectionEntity();
		
		//Get the request
		RequestEntity request = requestRepo.findById(id);
		
		//Get the associated users
		UserEntity sender = userService.retrieveUser(request.getSender().getId());
		UserEntity receiver = userService.retrieveUser(request.getReceiver().getId());
		
		connection.setFirstUser(sender);
		connection.setSecondUser(receiver);
		
		// Delete the request from the request table
		requestRepo.delete(request);
		
		// Save the connection in connections table
		connectionRepo.save(connection);
		
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public void rejectConnectionRequest(long id) {
		requestRepo.deleteById(id);
	}

	@Override
	public List<ConnectionRequestDto> retrieveConnectionsRequests() throws Exception {

		// Extract User's id from security context
		long userId = userService.retrieveUserId();

		// Call service to retrieve user's connections requests
		List<RequestEntity> requests = requestRepo.findReceiverByUserId(userId);

		List<ConnectionRequestDto> requestDtos = new ArrayList<>();

		for(RequestEntity req: requests){
			ConnectionRequestDto responseModel = new ConnectionRequestDto();

			// Copy properties to responseModel
			BeanUtils.copyProperties(req.getSender(), responseModel.getSenderDetails());
			BeanUtils.copyProperties(req.getSender().getUserDetails(), responseModel.getSenderDetails());
			BeanUtils.copyProperties(req.getSender().getUserDetails().getUserImage(), responseModel.getSenderDetails());

			requestDtos.add(responseModel);
		}

		return requestDtos;
	}

}
