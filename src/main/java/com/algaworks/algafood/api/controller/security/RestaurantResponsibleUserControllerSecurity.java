package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantResponsibleUserControllerSecurity {

    @HasAuthority.Restaurant.ResponsibleUser.Query
    ResponseEntity<List<UserResponse>> list(Long restaurantId);

    @HasAuthority.Restaurant.ResponsibleUser.Manage
    ResponseEntity<Void> add(Long restaurantId, Long userId);

    @HasAuthority.Restaurant.ResponsibleUser.Manage
    ResponseEntity<Void> remove(Long restaurantId, Long userId);
}
