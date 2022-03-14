package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.RestaurantRequest;
import com.algaworks.algafood.api.model.response.RestaurantResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantControllerSecurity {

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> list();

    @HasAuthority.Restaurant.Query
    ResponseEntity<RestaurantResponse> find(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> listByDeliveryFee(@ApiParam(value = "Delivery fee - Initial value", example = "10.00") BigDecimal initialFee,
                                                               @ApiParam(value = "Delivery fee - Final value", example = "20.00") BigDecimal finalFee);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> listByName(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<RestaurantResponse> findFirstByName(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> findTop2ByName(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> customSearch(@ApiParam(value = "Restaurant name", example = "McDonald's") String name,
                                                          @ApiParam(value = "Delivery fee - Initial value", example = "10.00") BigDecimal initialDeliveryFee,
                                                          @ApiParam(value = "Delivery fee - Final value", example = "20.00") BigDecimal finalDeliveryFee);

    @HasAuthority.Restaurant.Query
    ResponseEntity<Integer> countByCategory(@ApiParam(value = "Category identifier", example = "1") Long categoryId);

    @HasAuthority.Restaurant.Query
    ResponseEntity<List<RestaurantResponse>> findWithFreeDelivery(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @HasAuthority.Restaurant.Query
    ResponseEntity<RestaurantResponse> findFirst();

    @HasAuthority.Restaurant.Manage
    ResponseEntity<RestaurantResponse> create(@ApiParam(value = "Request body with data about the new restaurant") RestaurantRequest restaurantRequest);

    @HasAuthority.Restaurant.Manage
    ResponseEntity<RestaurantResponse> update(@ApiParam(value = "Restaurant identifier", example = "1") Long id,
                                              @ApiParam(value = "Request body with updated data about the existing restaurant") RestaurantRequest restaurantRequest);

    @HasAuthority.Restaurant.Manage
    ResponseEntity<Void> activate(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @HasAuthority.Restaurant.Manage
    ResponseEntity<Void> deactivate(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @HasAuthority.Restaurant.Manage
    ResponseEntity<Void> open(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @HasAuthority.Restaurant.Manage
    ResponseEntity<Void> close(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @HasAuthority.Restaurant.Manage
    ResponseEntity<Void> delete(@ApiParam(value = "Restaurant identifier", example = "1") Long id);
}
