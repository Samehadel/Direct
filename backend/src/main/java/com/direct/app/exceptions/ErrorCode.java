package com.direct.app.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ErrorCode {

	U$0001("Email: [%s] already exist!"),
	U$0002("User with ID: [%d] not exist!"),
	U$0003("User token doesn't match with sender ID: [%d]!"),
	U$0004("No connection requests with ID: [%d]!"),
	U$0005("Connection request with ID: [%d] can't be accepted with user [%d]!");

	@JsonValue
	@Getter
	private String errorMessage; 
	
	private ErrorCode(String error) {
		this.errorMessage = error;
	}
	
}
