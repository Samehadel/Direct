package com.direct.app.shared.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KeywordDto extends BaseDTO {

	private Long subscriptionId;
	private Integer keywordId;
	private Long userId;
	private String keywordDescription;
	private boolean subscribed;
	public KeywordDto(Integer keywordId, Long userId) {
		this.keywordId = keywordId;
		this.userId = userId;
	}
}
