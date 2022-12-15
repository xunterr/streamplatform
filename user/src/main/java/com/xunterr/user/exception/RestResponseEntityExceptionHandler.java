package com.xunterr.user.exception;

import com.xunterr.user.model.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { AlreadyTakenException.class })
    protected ResponseEntity<?> handleAlreadyTaken(RuntimeException ex, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return handleExceptionInternal(
                ex, new Response(false, error),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("id", ex.getId());
        error.put("error", ex.getMessage());
        return handleExceptionInternal(
                ex, new Response(false, error),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request){

        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(
                ex, new Response(false, errors),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }
}