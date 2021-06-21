package com.algaworks.algafood.api.exception.handler;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException e) {
        var error = Error.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Error> handleResourceInUseException(ResourceInUseException e) {
        var error = Error.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(CONFLICT).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Error> handleBusinessException(BusinessException e) {
        var error = Error.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        var error = Error.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE)
                .body(error);
    }
}
