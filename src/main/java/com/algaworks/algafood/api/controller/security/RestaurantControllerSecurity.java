package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.RestaurantRequest;
import com.algaworks.algafood.api.model.response.RestaurantResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantControllerSecurity {

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> list();

    @HasAuthority.Restaurant.Query
    ResponseEntity<RestaurantResponse> find(Long id);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> listByDeliveryFee(BigDecimal initialFee, BigDecimal finalFee);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> listByName(String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<RestaurantResponse> findFirstByName(String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> findTop2ByName(String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> customSearch(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee);

    @HasAuthority.Restaurant.Query
    ResponseEntity<Integer> countByCategory(Long categoryId);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> findWithFreeDelivery(String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<RestaurantResponse> findFirst();

    @HasAuthority.Restaurant.Manage
    ResponseEntity<RestaurantResponse> create(RestaurantRequest restaurantRequest);

    @HasAuthority.Restaurant.ManageOperation
    ResponseEntity<RestaurantResponse> update(Long restaurantId, RestaurantRequest restaurantRequest);

    @HasAuthority.Restaurant.ManageOperation
    ResponseEntity<Void> activate(Long restaurantId);

    @HasAuthority.Restaurant.ManageOperation
    ResponseEntity<Void> deactivate(Long restaurantId);

    @HasAuthority.Restaurant.ManageOperation
    ResponseEntity<Void> open(Long restaurantId);

    @HasAuthority.Restaurant.ManageOperation
    ResponseEntity<Void> close(Long restaurantId);

    @HasAuthority.Restaurant.ManageOperation
    ResponseEntity<Void> delete(Long restaurantId);
}
