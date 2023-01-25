package com.xunterr.stream.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException{

	@Getter
	private Long id;

	public EntityNotFoundException(Long id, String message){
		super(message);
		this.id = id;
	}

	public EntityNotFoundException(String message){
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}