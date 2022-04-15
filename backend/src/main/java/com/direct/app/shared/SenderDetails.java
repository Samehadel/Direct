package com.direct.app.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class SenderDetails {

    private String firstName;
    private String lastName;
    private String professionalTitle;
    private byte [] imageData;
    private String imageFormat;

}
