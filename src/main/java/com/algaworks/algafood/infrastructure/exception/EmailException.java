package com.algaworks.algafood.infrastructure.exception;

public class EmailException extends RuntimeException {

    private static final String MESSAGE = "It was not possible to send the e-mail";

    private static final long serialVersionUID = 1L;

    public EmailException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
