package com.algaworks.algafood.api.exception.util;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.exception.ErrorType;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class ExceptionHandlerUtil {

    private ExceptionHandlerUtil() {
    }

    public static Error buildError(HttpStatus status, ErrorType errorType, String detail) {
        return Error.builder()
                .status(status.value())
                .title(errorType.getType())
                .detail(detail)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Error buildInternalError(HttpStatus status, String title) {
        return Error.builder()
                .status(status.value())
                .title(title)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
