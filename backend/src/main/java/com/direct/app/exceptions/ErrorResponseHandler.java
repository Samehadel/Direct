package com.direct.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorResponseHandler extends ResponseEntityExceptionHandler {
	private  final Logger logger = LogManager.getLogger(ErrorResponseHandler.class);

	@ExceptionHandler(RuntimeBusinessException.class)
	@ResponseBody
	public ResponseEntity<ExceptionBody> handleBusinessExceptionInterface(RuntimeBusinessException e, WebRequest requestInfo , HttpServletRequest request) {
		ExceptionBody errorResponseDTO = new ExceptionBody(e.getErrorCode());
		logger.error(errorResponseDTO);
		return new ResponseEntity<>(errorResponseDTO,
				e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
