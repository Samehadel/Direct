package com.direct.app.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static java.lang.String.format;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RuntimeBusinessException extends ResponseStatusException {

    private static final long serialVersionUID = -8526630656781428983L;

    public RuntimeBusinessException(HttpStatus httpStatus, ErrorCode errorCode , Object...args) {
        super(httpStatus,
              new ExceptionBody(format(errorCode.getErrorMessage(), args), errorCode).toString());
    }
}
