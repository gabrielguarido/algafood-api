package com.algaworks.algafood.domain.exception;

public abstract class ResourceNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    protected ResourceNotFoundException(String message) {
        super(message);
    }
}
