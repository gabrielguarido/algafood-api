package com.algaworks.algafood.domain.exception;

public class StateNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "State %s not found";

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long stateId) {
        this(String.format(MESSAGE, stateId));
    }
}
