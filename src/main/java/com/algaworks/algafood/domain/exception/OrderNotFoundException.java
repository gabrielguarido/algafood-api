package com.algaworks.algafood.domain.exception;

import java.util.UUID;

public class OrderNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Order external key %s not found";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(UUID externalKey) {
        this(String.format(MESSAGE, externalKey.toString()));
    }
}
