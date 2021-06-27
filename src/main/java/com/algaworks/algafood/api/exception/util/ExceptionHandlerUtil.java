package com.algaworks.algafood.api.exception.util;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public final class ExceptionHandlerUtil {

    private ExceptionHandlerUtil() {
    }

    public static Error buildError(HttpStatus status, ErrorType errorType, String detail) {
        return Error.builder()
                .status(status.value())
                .title(errorType.getType())
                .detail(detail)
                .build();
    }
}
