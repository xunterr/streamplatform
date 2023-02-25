package com.xunterr.user.exception;

import lombok.Getter;

import java.util.UUID;

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
