package com.direct.app.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ExceptionBody {
    private String errorMessage;
    private ErrorCode errorCode;

    public ExceptionBody(String errorMessage, ErrorCode errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "{" +
                "'errorMessage':" + errorMessage  +
                ", 'errorCode':" + errorCode +
                '}';
    }
}
