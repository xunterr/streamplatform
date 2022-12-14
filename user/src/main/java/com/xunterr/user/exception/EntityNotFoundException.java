package com.xunterr.user.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException{

    @Getter
    private Long id;

    public EntityNotFoundException(Long id, String message){
        super(message);
        this.id = id;
    }
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
