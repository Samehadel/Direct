package com.direct.app.io.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubscriptionDTO extends BaseDTO{
	private Long id;
	private Long userId;
	private KeywordDto keyword;
}
