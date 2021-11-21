package com.algaworks.algafood.domain.exception;

public class ProfileNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Profile ID %s not found";

    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(Long profileId) {
        this(String.format(MESSAGE, profileId));
    }
}
