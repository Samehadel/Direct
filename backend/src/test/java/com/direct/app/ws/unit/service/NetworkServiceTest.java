package com.direct.app.ws.unit.service;

import com.direct.app.exceptions.ErrorCode;
import com.direct.app.exceptions.ExceptionBody;
import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.ConnectionEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.service.NetworkService;
import com.direct.app.service.UserService;
import com.direct.app.service.implementation.NetworkServiceImplementation;
import com.direct.app.service.implementation.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

import static com.direct.app.exceptions.ErrorCode.U$0008;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class NetworkServiceTest {
	@Mock
	private ConnectionRepository connectionRepoMock;

	@Mock
	private UserService userServiceMock;

	@Autowired
	private ObjectMapper mapper;

	@InjectMocks
	private NetworkService networkService;

	@Before
	public void init() {
		this.networkService = new NetworkServiceImplementation();
		mapper = new ObjectMapper();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void removeConnection_happy_path() throws Exception {
		Long currentUserId = 1L;
		Long connectionId = 20L;

		when(userServiceMock.getCurrentUserId())
				.thenReturn(currentUserId);
		when(connectionRepoMock.findByConnectionIdAndUserId(connectionId, currentUserId))
				.thenReturn(Optional.of(new ConnectionEntity()));

		networkService.removeConnection(connectionId);
		assertEquals(1, 1);
	}

	@Test
	public void removeConnection_unhappy_path_no_connection_with_current_user() throws Exception {
		Long currentUserId = 1L;
		Long connectionId = 20L;

		when(userServiceMock.getCurrentUserId())
				.thenReturn(currentUserId);
		when(connectionRepoMock.findByConnectionIdAndUserId(connectionId, currentUserId))
				.thenReturn(Optional.empty());

		try {
			networkService.removeConnection(connectionId);
			assertEquals(1, 0);
		}catch (RuntimeBusinessException ex){
			assertEquals(U$0008, ex.getErrorCode());
		}
	}
}
