package com.algaworks.algafood.domain.exception;

public class RestaurantNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Restaurant %s not found";

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long restaurantId) {
        this(String.format(MESSAGE, restaurantId));
    }
}
