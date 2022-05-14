package com.direct.app.ws.integration.commons;

import com.direct.app.exceptions.ErrorCode;
import com.direct.app.exceptions.ExceptionBody;
import com.direct.app.security.SecurityConstants;
import com.direct.app.shared.dto.ConnectionRequestDto;
import com.direct.app.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Component
public class TestCommons {
	@Autowired
	private JwtUtils jwtUtils;

	public HttpEntity getHttpEntity(ConnectionRequestDto connectionRequestDto, String token){
		HttpHeaders headers = addHeader_AuthorizationToken(token);

		return new HttpEntity(connectionRequestDto, headers);
	}

	public HttpEntity getHttpEntity(String body, String username){
		String token = generateUserAuthToken(username);
		HttpHeaders headers = addHeader_AuthorizationToken(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity(body, headers);
	}

	private HttpHeaders addHeader_AuthorizationToken(String token){
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstants.HEADER_STRING, token);

		return headers;
	}

	public Map<String, String> generateUsersAuthTokens(String[] usernames) {
		Map<String, String> authTokens = new HashMap<>();

		for (String username : usernames) {
			String token = generateUserAuthToken(username);
			authTokens.put(username, token);
		}
		return authTokens;
	}

	public String generateUserAuthToken(String username) {
		Map<String, String> usersPasswords = getUsersPasswords();
		String userPassword = ofNullable(usersPasswords.get(username))
								.orElse(null);
		String token = ofNullable(userPassword)
							.map(pass -> jwtUtils.getJWT(username, pass))
							.orElse("$2a$10$cwa.NIMgxz5RXjo5BuNdA.9eew4ldS6VlC.64ZEb8KvviKOfslHQq");
		token = SecurityConstants.TOKEN_PREFIX + token;

		return token;
	}

	private Map<String, String> getUsersPasswords(){
		Map<String, String> usersPasswords = new HashMap<>();

		usersPasswords.put("username_1", "password_1");
		usersPasswords.put("username_2", "password_2");
		usersPasswords.put("username_3", "password_3");
		usersPasswords.put("username_4", "password_4");

		return usersPasswords;
	}

	public boolean responseMatchErrorCode(ResponseEntity<Object> response, ErrorCode errorCode) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> responseBody = (LinkedHashMap) response.getBody();
		ExceptionBody responseErrorBody = mapper.readValue(responseBody.get("message"), ExceptionBody.class);

		return responseErrorBody.getErrorCode().equals(errorCode);
	}
}
