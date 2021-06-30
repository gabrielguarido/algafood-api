package com.algaworks.algafood.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class Error {

    private final Integer status;
    private final String title;
    private final String detail;
    private final LocalDateTime timestamp;
}
