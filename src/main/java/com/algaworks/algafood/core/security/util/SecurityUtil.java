package com.algaworks.algafood.core.security.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@UtilityClass
public class SecurityUtil {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getLoggedUserId() {
        var jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("userId");
    }
}
