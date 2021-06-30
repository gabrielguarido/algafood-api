package com.algaworks.algafood.api.exception.handler;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.algaworks.algafood.api.exception.ErrorType.BUSINESS_RULE_VIOLATION;
import static com.algaworks.algafood.api.exception.ErrorType.PAYLOAD_MALFORMED;
import static com.algaworks.algafood.api.exception.ErrorType.RESOURCE_IN_USE;
import static com.algaworks.algafood.api.exception.ErrorType.RESOURCE_NOT_FOUND;
import static com.algaworks.algafood.api.exception.util.ExceptionHandlerUtil.buildError;
import static com.algaworks.algafood.api.exception.util.ExceptionHandlerUtil.joinPath;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE = "The request body is invalid. Check syntax";
    private static final String INVALID_FORMAT_EXCEPTION_MESSAGE = "The property '%s' received a value '%s' which is of an invalid type. Inform a value that is compatible with the type '%s'";
    private static final String PROPERTY_NONEXISTENT = "The given property '%s' does not exist";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        var error = buildError(NOT_FOUND, RESOURCE_NOT_FOUND, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Object> handleResourceInUseException(ResourceInUseException ex, WebRequest request) {
        var error = buildError(CONFLICT, RESOURCE_IN_USE, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        var error = buildError(BAD_REQUEST, BUSINESS_RULE_VIOLATION, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        var error = buildError(status, PAYLOAD_MALFORMED, HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE);

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        final var detail = String.format(INVALID_FORMAT_EXCEPTION_MESSAGE, path, ex.getValue(), ex.getTargetType().getSimpleName());

        var error = buildError(status, PAYLOAD_MALFORMED, detail);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        final var detail = String.format(PROPERTY_NONEXISTENT, path);

        var error = buildError(status, PAYLOAD_MALFORMED, detail);

        return handleExceptionInternal(ex, error, headers, status, request);
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
