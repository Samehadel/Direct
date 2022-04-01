package com.direct.app.shared.dto;

import com.direct.app.shared.SenderDetails;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PublicationDto {
	private long id;
	private long senderId;
	private SenderDetails senderDetails;
	private String content;
	private String link;
	private boolean isRead;
	private List<Integer> keywords;

}
