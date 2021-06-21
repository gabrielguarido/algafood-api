package com.algaworks.algafood.domain.exception;

public class ResourceInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceInUseException(String message) {
        super(message);
    }
}
