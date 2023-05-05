package com.xunterr.stream.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("ALREADY_EXISTS")
public class AlreadyExistsException extends RuntimeException{
	public AlreadyExistsException(String message){
		super(message);
	}
	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}