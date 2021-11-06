package com.algaworks.algafood.domain.exception;

public class UserNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "User ID %s not found";

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        this(String.format(MESSAGE, userId));
    }
}
