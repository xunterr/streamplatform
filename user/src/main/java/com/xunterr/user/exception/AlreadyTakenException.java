package com.xunterr.user.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("ALREADY_TAKEN")
public class AlreadyTakenException extends RuntimeException{
    public AlreadyTakenException(String message){
        super(message);
    }
    public AlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
