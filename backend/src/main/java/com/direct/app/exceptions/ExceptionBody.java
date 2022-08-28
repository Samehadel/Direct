package com.direct.app.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class ExceptionBody {
    private String errorMessage;
    private String errorCode;

    public ExceptionBody(ErrorCode errorCode) {
        this.errorCode = errorCode.toString();
        this.errorMessage = errorCode.getErrorMessage();
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
