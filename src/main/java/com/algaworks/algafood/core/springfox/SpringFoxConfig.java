package com.algaworks.algafood.core.springfox;

import com.algaworks.algafood.api.exception.Error;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth2Scheme;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static com.algaworks.algafood.core.springfox.SpringFoxProperties.API_INFO_DESCRIPTION;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.API_INFO_TITLE;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.API_INFO_VERSION;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.BASE_PACKAGE;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.CONTACT;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.SWAGGER_RESOURCE_HANDLER;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.SWAGGER_RESOURCE_LOCATION;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.WEBJAR_RESOURCE_HANDLER;
import static com.algaworks.algafood.core.springfox.SpringFoxProperties.WEBJAR_RESOURCE_LOCATION;
import static com.algaworks.algafood.core.springfox.util.GlobalResponseMessagesUtil.globalDeleteResponseMessages;
import static com.algaworks.algafood.core.springfox.util.GlobalResponseMessagesUtil.globalGetResponseMessages;
import static com.algaworks.algafood.core.springfox.util.GlobalResponseMessagesUtil.globalPostPutResponseMessages;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();

        return new Docket(OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Error.class))
                .apiInfo(apiInfo())
                .tags(
                        new Tag("Cities", "Manages the cities"),
                        new Tag("Profiles", "Manages the user's profiles"),
                        new Tag("Categories", "Manages the food categories"),
                        new Tag("Payment Methods", "Manages the payment methods for specific restaurants"),
                        new Tag("Orders", "Manages the orders"),
                        new Tag("Restaurants", "Manages the restaurants"),
                        new Tag("States", "Manages the states"),
                        new Tag("Products", "Manages the products for specific restaurants"),
                        new Tag("Users", "Manages the users"),
                        new Tag("Statistics", "Provides statistics about the business side")
                )
                .securitySchemes(List.of(securityScheme()))
                .securityContexts(List.of(securityContext()));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_INFO_TITLE)
                .description(API_INFO_DESCRIPTION)
                .version(API_INFO_VERSION)
                .contact(CONTACT)
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(SWAGGER_RESOURCE_HANDLER)
                .addResourceLocations(SWAGGER_RESOURCE_LOCATION);

        registry.addResourceHandler(WEBJAR_RESOURCE_HANDLER)
                .addResourceLocations(WEBJAR_RESOURCE_LOCATION);
    }

    private SecurityScheme securityScheme() {
        return OAuth2Scheme.OAUTH2_PASSWORD_FLOW_BUILDER
                .name("AlgaFood")
                .scopes(authorizationScopes())
                .tokenUrl("/oauth/token")
                .build();
    }

    private List<AuthorizationScope> authorizationScopes() {
        return Arrays.asList(
                new AuthorizationScope("READ", "Read access to resources"),
                new AuthorizationScope("WRITE", "Write access to resources")
        );
    }

    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("AlgaFood")
                .scopes(authorizationScopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(List.of(securityReference))
                .operationSelector(operationContext -> true)
                .build();
    }
}
