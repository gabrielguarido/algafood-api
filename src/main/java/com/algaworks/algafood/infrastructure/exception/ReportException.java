package com.algaworks.algafood.infrastructure.exception;

public class ReportException extends RuntimeException {

    private static final String MESSAGE = "It was not possible to issue the %s report";

    private static final long serialVersionUID = 1L;

    public ReportException(String report) {
        super(String.format(MESSAGE, report));
    }

    public ReportException(String report, Throwable cause) {
        super(String.format(MESSAGE, report), cause);
    }
}
