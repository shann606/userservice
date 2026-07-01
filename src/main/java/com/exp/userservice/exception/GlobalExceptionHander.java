package com.exp.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHander {
	
	private final  String failedStatus ="Failed";

	@ExceptionHandler(value = UserAlreadyFoundException.class)
	public ResponseEntity<ExceptionResponse> userExists(UserAlreadyFoundException ex) {
		log.warn(ex.getReason());
		ExceptionResponse res = new ExceptionResponse(failedStatus, ex.getReason());

		return new ResponseEntity<ExceptionResponse>(res, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> userNotfound(UserNotFoundException ex){
		log.warn(ex.getReason());
		ExceptionResponse res = new ExceptionResponse(failedStatus, ex.getReason());

		return new ResponseEntity<ExceptionResponse>(res, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> systemException(Exception ex) {
		log.error(ex.getMessage());
		ExceptionResponse res = new ExceptionResponse(failedStatus, ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
