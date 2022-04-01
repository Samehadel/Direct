package com.direct.app.shared.dto;

import com.direct.app.shared.SenderDetails;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectionRequestDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private SenderDetails senderDetails;

    public ConnectionRequestDto(Long senderId, Long receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
