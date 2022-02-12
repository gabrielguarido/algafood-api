package com.algaworks.algafood.domain.exception;

public class FileUploadException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "An error occurred while uploading the product picture %s. Product ID: %s, Restaurant ID: %s";

    public FileUploadException(String fileName, Long productId, Long restaurantId) {
        super(String.format(MESSAGE, fileName, productId, restaurantId));
    }
}
