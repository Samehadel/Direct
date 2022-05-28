package com.direct.app.io.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SenderDetails {

    private String firstName;
    private String lastName;
    private String professionalTitle;
    private byte [] imageData;
    private String imageFormat;

}
