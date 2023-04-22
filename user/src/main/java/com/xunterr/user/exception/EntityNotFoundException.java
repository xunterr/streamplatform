package com.xunterr.user.exception;

import io.github.wimdeblauwe.errorhandlingspringbootstarter.ResponseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseErrorCode("USER_NOT_FOUND")
public class EntityNotFoundException extends RuntimeException{

    @Getter
    private Object request;

    public EntityNotFoundException(Object request, String message){
        super(message);
        this.request = request;
    }
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
