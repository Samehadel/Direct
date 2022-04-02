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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.direct.app.exceptions.ErrorCode.U$0004;
import static com.direct.app.exceptions.ErrorCode.U$0005;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class ConnectionRequestServiceImplementation implements ConnectionRequestService {

	@Autowired
	UserService userService;
	
	@Autowired
	RequestRepository connectionRequestsRepo;
	
	@Autowired
	ConnectionRepository connectionRepo;
	
	@Override
	public ConnectionRequestDto createConnectionRequest(ConnectionRequestDto connectionRequestDto) throws Exception {

		validateSender(connectionRequestDto.getSenderId());
		RequestEntity newRequest = new RequestEntity();

		// Retrieve the two users from database
		UserEntity sender = userService.retrieveUserById(connectionRequestDto.getSenderId());
		UserEntity receiver = userService.retrieveUserById(connectionRequestDto.getReceiverId());

		assignConnectionRequestSender(sender, newRequest);
		assignConnectionRequestReceiver(receiver, newRequest);

		// Save the request
		connectionRequestsRepo.save(newRequest);
		
		// Copy the properties to DTO
		connectionRequestDto.setId(newRequest.getId());
		
		return connectionRequestDto;
	}

	private void validateSender(Long senderId) throws Exception{
		// Get username from security context
		Long currentUserId = userService.getCurrentUserId();

		if(currentUserId != senderId)
			throw  new RuntimeBusinessException(NOT_ACCEPTABLE, ErrorCode.U$0003, senderId);

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
	public boolean acceptConnectionRequest(long connectionRequestId) throws Exception{

		//Prepare connection object
		ConnectionEntity connection = new ConnectionEntity();
		
		//Get the request
		RequestEntity request = connectionRequestsRepo
				.findById(connectionRequestId)
				.orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0004, connectionRequestId));

		checkCurrentUserIsReceiver(request);

		//Get the associated users
		UserEntity sender = userService.retrieveUserById(request.getSender().getId());
		UserEntity receiver = userService.retrieveUserById(request.getReceiver().getId());
		
		connection.setFirstUser(sender);
		connection.setSecondUser(receiver);
		
		// Delete the request from the request table
		connectionRequestsRepo.delete(request);
		
		// Save the connection in connections table
		connectionRepo.save(connection);
		

		return true;
	}

	private void checkCurrentUserIsReceiver(RequestEntity request) throws Exception {
		Long currentUserId = userService.getCurrentUserId();
		if(request.getReceiver().getId() != currentUserId)
			throw new RuntimeBusinessException(NOT_ACCEPTABLE, U$0005, request.getId(), currentUserId);
	}

	@Override
	public void rejectConnectionRequest(long id) {
		connectionRequestsRepo.deleteById(id);
	}

	@Override
	public List<ConnectionRequestDto> retrieveConnectionsRequests() throws Exception {

		// Extract User's id from security context
		long userId = userService.getCurrentUserId();

		// Call service to retrieve user's connections requests
		List<RequestEntity> requests = connectionRequestsRepo.findReceiverByUserId(userId);

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
