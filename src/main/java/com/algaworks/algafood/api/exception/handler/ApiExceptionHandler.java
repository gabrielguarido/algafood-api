package com.algaworks.algafood.api.exception.handler;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.algaworks.algafood.api.exception.ErrorType.RESOURCE_IN_USE;
import static com.algaworks.algafood.api.exception.ErrorType.RESOURCE_NOT_FOUND;
import static com.algaworks.algafood.api.exception.ErrorType.BUSINESS_RULE_VIOLATION;
import static com.algaworks.algafood.api.exception.util.ExceptionHandlerUtil.buildError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Error error = buildError(NOT_FOUND, RESOURCE_NOT_FOUND, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Object> handleResourceInUseException(ResourceInUseException ex, WebRequest request) {
        Error error = buildError(CONFLICT, RESOURCE_IN_USE, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        Error error = buildError(BAD_REQUEST, BUSINESS_RULE_VIOLATION, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Error.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Error.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
