package com.algaworks.algafood.api.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    RESOURCE_NOT_FOUND("Resource not found"),
    RESOURCE_IN_USE("Resource in use"),
    BUSINESS_RULE_VIOLATION("Business rule violation");

    private final String type;

    ErrorType(String type) {
        this.type = type;
    }
}
