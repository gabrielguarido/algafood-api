package com.algaworks.algafood.domain.exception;

public class CityNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "City ID %s not found";

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long cityId) {
        this(String.format(MESSAGE, cityId));
    }
}
