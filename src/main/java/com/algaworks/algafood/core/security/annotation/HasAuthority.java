package com.algaworks.algafood.core.security.annotation;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface HasAuthority {

    @interface Category {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_CATEGORY')")
        @interface Manage {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @interface Query {
        }
    }

    @interface Restaurant {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_RESTAURANT')")
        @interface Manage {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('MANAGE_RESTAURANT') or @securityUtil.managesOperation(#restaurantId))")
        @interface ManageOperation {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @interface Query {
        }

        @interface Product {

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PRODUCT')")
            @interface Manage {
            }

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_READ')")
            @interface Query {
            }
        }

        @interface PaymentMethod {

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PAYMENT_METHOD')")
            @interface Manage {
            }

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_READ')")
            @interface Query {
            }
        }

        @interface ResponsibleUser {

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_RESPONSIBLE_USER')")
            @interface Manage {
            }

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_RESPONSIBLE_USER')")
            @interface Query {
            }
        }
    }

    @interface Order {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE')")
        @interface Issue {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_ORDER') and @securityUtil.orderIsManagedBy(#orderId)")
        @interface Manage {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and (@securityUtil.loggedUserId == #filter.clientId or @securityUtil.managesOperation(#filter.restaurantId))")
        @interface Query {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @PostAuthorize("@securityUtil.loggedUserId == returnObject.client.id or @securityUtil.managesOperation(returnObject.restaurant.id)")
        @interface Find {
        }
    }
}
