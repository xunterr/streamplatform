package com.xunterr.user.exception;

import lombok.Getter;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException{

    @Getter
    private UUID id;

    public EntityNotFoundException(UUID id, String message){
        super(message);
        this.id = id;
    }
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
