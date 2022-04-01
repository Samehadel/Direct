package com.direct.app.shared.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileDto {

	private long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String majorField;
	private String bio;
	private String professionalTitle;
	private byte [] imageData;
	private String imageFormat;

	public ProfileDto(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
