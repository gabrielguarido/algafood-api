package com.algaworks.algafood.core.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface HasAuthority {

    @interface Category {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('MANAGE_CATEGORY')")
        @interface Manage {
        }
    }
}
