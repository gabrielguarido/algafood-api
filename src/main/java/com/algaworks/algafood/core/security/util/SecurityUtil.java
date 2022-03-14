package com.algaworks.algafood.core.security.util;

import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final RestaurantRepository restaurantRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public SecurityUtil(RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
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

    public boolean orderIsManagedBy(String orderId) {
        return orderRepository.isManagedBy(orderId, getLoggedUserId());
    }
}
