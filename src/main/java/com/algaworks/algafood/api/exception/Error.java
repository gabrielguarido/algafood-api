package com.algaworks.algafood.api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Error {

    private final LocalDateTime timestamp;
    private final String message;
}
