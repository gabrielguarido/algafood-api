package com.algaworks.algafood.api.exception.handler;

public final class ApiExceptionMessages {
    private ApiExceptionMessages() {
    }

    protected static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE = "The request body is invalid. Check syntax";
    protected static final String INVALID_FORMAT_EXCEPTION_MESSAGE = "The property '%s' received a value '%s' which is of an invalid type. Inform a value that is compatible with the type '%s'";
    protected static final String PROPERTY_NONEXISTENT_MESSAGE = "The given property '%s' does not exist";
    protected static final String INVALID_PARAMETER_MESSAGE = "The URL parameter '%s' received the value '%s', which is of an invalid type. Inform a value that is compatible with the type '%s'";
    protected static final String INVALID_PAYLOAD_MESSAGE = "One or more fields are invalid. Fill in correctly and try again";
    protected static final String RESOURCE_NOT_FOUND_MESSAGE = "The resource '%s' does not exist";
    protected static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected internal error occurred. Try again, and if the problem persist, contact the system admin";
}
