package com.algaworks.algafood.api.exception.util;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.exception.ErrorType;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ExceptionHandlerUtil {

    public static Error buildError(HttpStatus status, String title) {
        return Error.builder()
                .status(status.value())
                .title(title)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public static Error buildError(HttpStatus status, ErrorType errorType, String detail) {
        return Error.builder()
                .status(status.value())
                .title(errorType.getType())
                .detail(detail)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public static Error buildError(HttpStatus status, ErrorType errorType, String detail, List<Error.Field> fields) {
        return Error.builder()
                .status(status.value())
                .title(errorType.getType())
                .detail(detail)
                .timestamp(OffsetDateTime.now())
                .fields(fields)
                .build();
    }

    public static List<Error.Field> buildErrorFields(MethodArgumentNotValidException ex, MessageSource messageSource) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    String detail = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return Error.Field.builder()
                            .name(fieldError.getField())
                            .detail(detail)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static List<Error.Field> buildErrorFields(BindingResult bindingResult, MessageSource messageSource) {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    String detail = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return Error.Field.builder()
                            .name(fieldError.getField())
                            .detail(detail)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
