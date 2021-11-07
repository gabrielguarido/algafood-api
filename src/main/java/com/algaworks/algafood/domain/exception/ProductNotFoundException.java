package com.algaworks.algafood.domain.exception;

public class ProductNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Product ID %s not found for Restaurant ID %s";

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long productId, Long restaurantId) {
        this(String.format(MESSAGE, productId, restaurantId));
    }
}
