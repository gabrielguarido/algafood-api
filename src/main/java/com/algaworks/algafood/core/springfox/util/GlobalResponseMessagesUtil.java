package com.algaworks.algafood.core.springfox.util;

import lombok.experimental.UtilityClass;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.Response;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@UtilityClass
public class GlobalResponseMessagesUtil {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    private static final String BAD_REQUEST_MESSAGE = "Invalid request (client-side error)";
    private static final String UNSUPPORTED_MEDIA_TYPE_MESSAGE = "Request refused due to unsupported request body format";
    private static final String NOT_ACCEPTABLE_MESSAGE = "The resource does not have a representation that can be accepted by the consumer";

    public static List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(INTERNAL_SERVER_ERROR.value()))
                        .description(INTERNAL_SERVER_ERROR_MESSAGE)
                        .representation(APPLICATION_JSON)
                        .apply(getErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(NOT_ACCEPTABLE.value()))
                        .description(NOT_ACCEPTABLE_MESSAGE)
                        .build()
        );
    }

    public static List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(BAD_REQUEST.value()))
                        .description(BAD_REQUEST_MESSAGE)
                        .representation(APPLICATION_JSON)
                        .apply(getErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(INTERNAL_SERVER_ERROR.value()))
                        .description(INTERNAL_SERVER_ERROR_MESSAGE)
                        .representation(APPLICATION_JSON)
                        .apply(getErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(NOT_ACCEPTABLE.value()))
                        .description(NOT_ACCEPTABLE_MESSAGE)
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(UNSUPPORTED_MEDIA_TYPE.value()))
                        .description(UNSUPPORTED_MEDIA_TYPE_MESSAGE)
                        .representation(APPLICATION_JSON)
                        .apply(getErrorModelReference())
                        .build()
        );
    }

    public static List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(BAD_REQUEST.value()))
                        .description(BAD_REQUEST_MESSAGE)
                        .representation(APPLICATION_JSON)
                        .apply(getErrorModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(INTERNAL_SERVER_ERROR.value()))
                        .description(INTERNAL_SERVER_ERROR_MESSAGE)
                        .representation(APPLICATION_JSON)
                        .apply(getErrorModelReference())
                        .build()
        );
    }

    private static Consumer<RepresentationBuilder> getErrorModelReference() {
        return representation -> representation.model(model -> model.name("Error")
                .referenceModel(reference -> reference.key(key -> key.qualifiedModelName(
                        qualified -> qualified.name("Error").namespace("com.algaworks.algafood.api.exception")))
                )
        );
    }
}
