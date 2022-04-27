package com.direct.app.ws.integration.commons;

import com.direct.app.security.SecurityConstants;
import com.direct.app.shared.dto.ConnectionRequestDto;
import com.direct.app.utils.JwtUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestCommons {
	@Autowired
	private JwtUtils jwtUtils;

	public HttpEntity getHttpEntity(ConnectionRequestDto connectionRequestDto, String token){
		HttpHeaders headers = addHeader_AuthorizationToken(token);

		return new HttpEntity(connectionRequestDto, headers);
	}

	public HttpEntity getHttpEntity(String body, String token){
		HttpHeaders headers = addHeader_AuthorizationToken(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity(body, headers);
	}

	private HttpHeaders addHeader_AuthorizationToken(String token){
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstants.HEADER_STRING, token);

		return headers;
	}

	public JSONObject json() {
		return new JSONObject();
	}

	public JSONArray jsonArray() {
		return new JSONArray();
	}

	public Map<String, String> generateUsersAuthTokens(String[] usernames) {
		Map<String, String> usersPasswords = getUsersPasswords();
		Map<String, String> authTokens = new HashMap<>();

		for (String username : usernames) {
			String token = jwtUtils.getJWT(username, usersPasswords.get(username));
			token = SecurityConstants.TOKEN_PREFIX + token;
			authTokens.put(username, token);
		}
		return authTokens;
	}

	private Map<String, String> getUsersPasswords(){
		Map<String, String> usersPasswords = new HashMap<>();

		usersPasswords.put("username_1", "password_1");
		usersPasswords.put("username_2", "password_2");
		usersPasswords.put("username_3", "password_3");
		usersPasswords.put("username_4", "password_4");

		return usersPasswords;
	}
}
