package com.algaworks.algafood.domain.exception;

public class OrderNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Order ID %s not found";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long orderId) {
        this(String.format(MESSAGE, orderId));
    }
}
