package com.algaworks.algafood.api.exception.handler;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.exception.ValidationException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.algaworks.algafood.api.exception.ErrorType.BUSINESS_RULE_VIOLATION;
import static com.algaworks.algafood.api.exception.ErrorType.INTERNAL_SERVER_ERROR;
import static com.algaworks.algafood.api.exception.ErrorType.INVALID_PARAMETER;
import static com.algaworks.algafood.api.exception.ErrorType.INVALID_PAYLOAD;
import static com.algaworks.algafood.api.exception.ErrorType.PAYLOAD_MALFORMED;
import static com.algaworks.algafood.api.exception.ErrorType.RESOURCE_IN_USE;
import static com.algaworks.algafood.api.exception.ErrorType.RESOURCE_NOT_FOUND;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.INTERNAL_SERVER_ERROR_MESSAGE;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.INVALID_FORMAT_EXCEPTION_MESSAGE;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.INVALID_PARAMETER_MESSAGE;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.INVALID_PAYLOAD_MESSAGE;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.PROPERTY_NONEXISTENT_MESSAGE;
import static com.algaworks.algafood.api.exception.handler.ApiExceptionMessages.RESOURCE_NOT_FOUND_MESSAGE;
import static com.algaworks.algafood.api.exception.util.ExceptionHandlerUtil.buildError;
import static com.algaworks.algafood.api.exception.util.ExceptionHandlerUtil.buildErrorFields;
import static com.algaworks.algafood.api.exception.util.ExceptionHandlerUtil.joinPath;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error(ex.getMessage(), ex);

        var error = buildError(status, INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        var error = buildError(NOT_FOUND, RESOURCE_NOT_FOUND, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Object> handleResourceInUse(ResourceInUseException ex, WebRequest request) {
        var error = buildError(CONFLICT, RESOURCE_IN_USE, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
        var error = buildError(BAD_REQUEST, BUSINESS_RULE_VIOLATION, ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return handleInternalValidation(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInternalValidation(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        var error = buildError(status, PAYLOAD_MALFORMED, HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE);

        return handleExceptionInternal(ex, error, new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(status)
                .headers(headers)
                .build();
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        final var detail = String.format(INVALID_FORMAT_EXCEPTION_MESSAGE, path, ex.getValue(), ex.getTargetType().getSimpleName());

        var error = buildError(status, PAYLOAD_MALFORMED, detail);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        final var detail = String.format(PROPERTY_NONEXISTENT_MESSAGE, path);

        var error = buildError(status, PAYLOAD_MALFORMED, detail);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var detail = String.format(INVALID_PARAMETER_MESSAGE, ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        var error = buildError(status, INVALID_PARAMETER, detail);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var detail = String.format(RESOURCE_NOT_FOUND_MESSAGE, ex.getRequestURL());

        var error = buildError(status, RESOURCE_NOT_FOUND, detail);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleInternalValidation(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleInternalValidation(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errorFields = buildErrorFields(bindingResult, messageSource);

        var error = buildError(status, INVALID_PAYLOAD, INVALID_PAYLOAD_MESSAGE, errorFields);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = buildError(status, status.getReasonPhrase());
        } else if (body instanceof String) {
            body = buildError(status, (String) body);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
