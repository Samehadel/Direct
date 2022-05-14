package com.direct.app.shared.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileImageDTO extends BaseDTO{
	private long id;
	private String imageName;
	private String imageUrl;
	private String imageFormat;
	private byte [] imageData;
}
