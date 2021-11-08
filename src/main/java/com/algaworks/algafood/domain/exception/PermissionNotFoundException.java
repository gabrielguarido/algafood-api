package com.algaworks.algafood.domain.exception;

public class PermissionNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Permission ID %s not found";

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long permissionId) {
        this(String.format(MESSAGE, permissionId));
    }
}
