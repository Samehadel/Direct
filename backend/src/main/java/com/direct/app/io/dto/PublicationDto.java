package com.direct.app.io.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PublicationDto extends BaseDTO {
	private Long id;
	private Long senderId;
	private SenderDetails senderDetails;
	private String content;
	private String link;
	private Boolean isRead;
	private List<Integer> keywords;

	public PublicationDto() {
		this.senderDetails = new SenderDetails();
	}
}
