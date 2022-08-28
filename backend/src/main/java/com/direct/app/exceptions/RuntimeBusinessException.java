package com.direct.app.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RuntimeBusinessException extends RuntimeException implements BusinessExceptionInterface{

    private static final long serialVersionUID = -8526630656781428983L;
    private String errorMessage;
    private ErrorCode errorCode;
    private HttpStatus httpStatus;

    public RuntimeBusinessException(HttpStatus httpStatus, ErrorCode errorCode , Object...args) {
        super(format(errorCode.getErrorMessage(), args));
        this.errorMessage = format(errorCode.getErrorMessage(), args);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
