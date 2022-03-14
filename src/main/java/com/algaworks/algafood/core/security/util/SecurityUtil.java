package com.algaworks.algafood.core.security.util;

import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public SecurityUtil(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getLoggedUserId() {
        var jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("userId");
    }

    public boolean managesOperation(Long restaurantId) {
        return restaurantRepository.existsResponsibleUser(restaurantId, getLoggedUserId());
    }
}
