package com.algaworks.algafood.domain.exception;

public class ProductPictureNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Picture not found for Product ID %s";

    public ProductPictureNotFoundException(String message) {
        super(message);
    }

    public ProductPictureNotFoundException(Long productId) {
        this(String.format(MESSAGE, productId));
    }
}
