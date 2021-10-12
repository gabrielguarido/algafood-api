package com.algaworks.algafood.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class Error {

    private final Integer status;
    private final OffsetDateTime timestamp;
    private final String title;
    private final String detail;
    private final List<Field> fields;

    @Getter
    @Builder
    public static class Field {

        private final String name;
        private final String detail;
    }
}
