package com.xunterr.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyTakenException extends RuntimeException{
    public AlreadyTakenException(String message){
        super(message);
    }
    public AlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
