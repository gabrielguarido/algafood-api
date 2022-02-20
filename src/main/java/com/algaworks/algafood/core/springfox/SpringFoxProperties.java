package com.algaworks.algafood.core.springfox;

public final class SpringFoxProperties {

    private SpringFoxProperties() {
    }

    public static final String BASE_PACKAGE = "com.algaworks.algafood.api";
    public static final String API_INFO_TITLE = "AlgaFood API";
    public static final String API_INFO_DESCRIPTION = "AlgaFood API";
    public static final String API_INFO_VERSION = "1.1.8";

    public static final String SWAGGER_RESOURCE_HANDLER = "swagger-ui.html";
    public static final String SWAGGER_RESOURCE_LOCATION = "classpath:/META-INF/resources/";

    public static final String WEBJAR_RESOURCE_HANDLER = "/webjars/**";
    public static final String WEBJAR_RESOURCE_LOCATION = "classpath:/META-INF/resources/webjars/";
}
