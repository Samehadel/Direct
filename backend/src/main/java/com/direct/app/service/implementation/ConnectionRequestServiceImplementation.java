package com.direct.app.service.implementation;

import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.io.entities.RequestEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.RequestRepository;
import com.direct.app.service.IConnectionRequestService;
import com.direct.app.service.IUserService;
import com.direct.app.shared.dto.RequestDto;
import com.direct.app.ui.models.response.RequestsResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionRequestServiceImplementation implements IConnectionRequestService {

	@Autowired
	IUserService userService; 
	
	@Autowired
	RequestRepository requestRepo; 
	
	@Autowired
	ConnectionRepository connectionRepo;
	
	@Override
	public RequestDto createConnectionRequest(long receiverId) throws Exception {

		// Get username from security context
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		RequestEntity newRequest = new RequestEntity();
		RequestDto requestDto = new RequestDto();

		// Retrieve the two users from database
		UserEntity sender = userService.retrieveUser(username);
		UserEntity receiver = userService.retrieveUser(receiverId);
		
		// Assign relationships
		sender.addSentRequest(newRequest);
		receiver.addReceivedRequest(newRequest);
		
		newRequest.setSender(sender);
		newRequest.setReceiver(receiver);
		
		// Save the request
		RequestEntity backRequest = requestRepo.save(newRequest);
		
		// Copy the properties to DTO
		requestDto.setId(backRequest.getId());
		requestDto.setSenderId(sender.getId());
		
		return requestDto;
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
	public List<RequestsResponseModel> retrieveConnectionsRequests() throws Exception {

		// Extract User's id from security context
		long userId = userService.retrieveUserId();

		// Call service to retrieve user's connections requests
		List<RequestEntity> requests = requestRepo.findReceiverByUserId(userId);

		List<RequestsResponseModel> responseModels = new ArrayList<>();

		for(RequestEntity req: requests){
			RequestsResponseModel responseModel = new RequestsResponseModel();

			// Copy properties to responseModel
			BeanUtils.copyProperties(req.getSender(), responseModel.getSenderDetails());
			BeanUtils.copyProperties(req.getSender().getUserDetails(), responseModel.getSenderDetails());
			BeanUtils.copyProperties(req.getSender().getUserDetails().getUserImage(), responseModel.getSenderDetails());

			responseModels.add(responseModel);
		}

		return responseModels;
	}

}
