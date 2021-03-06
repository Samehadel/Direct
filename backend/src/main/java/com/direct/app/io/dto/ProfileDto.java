package com.direct.app.io.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileDto extends BaseDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String phone;
	private String majorField;
	private String bio;
	private String professionalTitle;
	private Byte [] imageData;
	private String imageFormat;

	public ProfileDto(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
