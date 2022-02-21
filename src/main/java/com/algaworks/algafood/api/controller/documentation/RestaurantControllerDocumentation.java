package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.RestaurantRequest;
import com.algaworks.algafood.api.model.response.RestaurantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Restaurants")
public interface RestaurantControllerDocumentation {

    @ApiOperation("Lists all the restaurants that are available")
    ResponseEntity<List<RestaurantResponse>> list();

    @ApiOperation("Finds a restaurant by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<RestaurantResponse> find(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @ApiOperation("Lists all the restaurants that are available, filtering by delivery fee range")
    ResponseEntity<List<RestaurantResponse>> listByDeliveryFee(@ApiParam(value = "Delivery fee - Initial value", example = "10.00") BigDecimal initialFee,
                                                               @ApiParam(value = "Delivery fee - Final value", example = "20.00") BigDecimal finalFee);

    @ApiOperation("Lists all the restaurants that are available, filtering by name")
    ResponseEntity<List<RestaurantResponse>> listByName(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @ApiOperation("Returns the first restaurant found in the search query, filtering by name")
    ResponseEntity<RestaurantResponse> findFirstByName(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @ApiOperation("Returns the first two restaurants found in the search query, filtering by name")
    ResponseEntity<List<RestaurantResponse>> findTop2ByName(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @ApiOperation("Searches for specific restaurants using a search filter")
    ResponseEntity<List<RestaurantResponse>> customSearch(@ApiParam(value = "Restaurant name", example = "McDonald's") String name,
                                                          @ApiParam(value = "Delivery fee - Initial value", example = "10.00") BigDecimal initialDeliveryFee,
                                                          @ApiParam(value = "Delivery fee - Final value", example = "20.00") BigDecimal finalDeliveryFee);

    @ApiOperation("Counts how many restaurants are registered for a specific category")
    ResponseEntity<Integer> countByCategory(@ApiParam(value = "Category identifier", example = "1") Long categoryId);

    @ApiOperation("Lists all the restaurants that offer free delivery")
    ResponseEntity<List<RestaurantResponse>> findWithFreeDelivery(@ApiParam(value = "Restaurant name", example = "McDonald's") String name);

    @ApiOperation("Returns the first restaurant found in the search query")
    ResponseEntity<RestaurantResponse> findFirst();

    @ApiOperation("Registers a new restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurant registered successfully")
    })
    ResponseEntity<RestaurantResponse> create(@ApiParam(value = "Request body with data about the new restaurant") RestaurantRequest restaurantRequest);

    @ApiOperation("Updates an existing restaurant with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurant updated successfully"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<RestaurantResponse> update(@ApiParam(value = "Restaurant identifier", example = "1") Long id,
                                              @ApiParam(value = "Request body with updated data about the existing restaurant") RestaurantRequest restaurantRequest);

    @ApiOperation("Activates an existing restaurant by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant activated successfully"),
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<Void> activate(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @ApiOperation("Deactivates an existing restaurant by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant deactivated successfully"),
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<Void> deactivate(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @ApiOperation("Opens an existing restaurant by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant opened successfully"),
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<Void> open(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @ApiOperation("Closes an existing restaurant by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant closed successfully"),
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<Void> close(@ApiParam(value = "Restaurant identifier", example = "1") Long id);

    @ApiOperation("Deletes an existing restaurant by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant removed successfully"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<Void> delete(@ApiParam(value = "Restaurant identifier", example = "1") Long id);
}
