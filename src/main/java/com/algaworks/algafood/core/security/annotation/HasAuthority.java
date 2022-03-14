package com.algaworks.algafood.core.security.annotation;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@PreAuthorize("isAuthenticated()")
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
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_RESTAURANT') and @securityUtil.managesOperation(#restaurantId)")
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
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PRODUCT') and @securityUtil.managesOperation(#restaurantId)")
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
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PAYMENT_METHOD') and @securityUtil.managesOperation(#restaurantId)")
            @interface ManageOperation {
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
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_RESPONSIBLE_USER') and @securityUtil.managesOperation(#restaurantId)")
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
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_ORDER') and @securityUtil.orderIsManagedBy(#externalKey)")
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
        @PostAuthorize("@securityUtil.loggedUserId == returnObject.getBody().client.id or @securityUtil.managesOperation(returnObject.getBody().restaurant.id)")
        @interface Find {
        }
    }

    @interface City {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_CITY')")
        @interface Manage {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @interface Query {
        }
    }

    @interface State {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_STATE')")
        @interface Manage {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @interface Query {
        }
    }

    @interface User {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE')")
        @interface Manage {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @securityUtil.loggedUserId == #id")
        @interface Update {
        }

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @interface Query {
        }

        @interface Profile {

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PROFILE')")
            @interface Manage {
            }

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PROFILE') and @securityUtil.loggedUserId == #userId")
            @interface ManageOperation {
            }

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_PROFILE')")
            @interface Query {
            }
        }

        @interface Permission {

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('MANAGE_PERMISSION')")
            @interface Manage {
            }

            @Target(METHOD)
            @Retention(RUNTIME)
            @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_PERMISSION')")
            @interface Query {
            }
        }
    }

    @interface Statistics {

        @Target(METHOD)
        @Retention(RUNTIME)
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_STATISTICS')")
        @interface Query {
        }
    }
}
