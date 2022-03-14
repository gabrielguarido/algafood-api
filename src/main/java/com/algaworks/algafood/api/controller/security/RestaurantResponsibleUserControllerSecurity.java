package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantResponsibleUserControllerSecurity {

    @HasAuthority.Restaurant.ResponsibleUser.Query
    ResponseEntity<List<UserResponse>> list(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId);

    @HasAuthority.Restaurant.ResponsibleUser.Manage
    ResponseEntity<Void> add(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                             @ApiParam(value = "Responsible user identifier", example = "1") Long userId);

    @HasAuthority.Restaurant.ResponsibleUser.Manage
    ResponseEntity<Void> remove(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                @ApiParam(value = "Responsible user identifier", example = "1") Long userId);
}
