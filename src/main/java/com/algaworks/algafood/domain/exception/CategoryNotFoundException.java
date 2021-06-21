package com.algaworks.algafood.domain.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Category %s not found";

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long categoryId) {
        this(String.format(MESSAGE, categoryId));
    }
}
