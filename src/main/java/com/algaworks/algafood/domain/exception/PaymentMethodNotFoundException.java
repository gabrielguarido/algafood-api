package com.algaworks.algafood.domain.exception;

public class PaymentMethodNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Payment Method ID %s not found";

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(Long paymentMethodId) {
        this(String.format(MESSAGE, paymentMethodId));
    }
}
