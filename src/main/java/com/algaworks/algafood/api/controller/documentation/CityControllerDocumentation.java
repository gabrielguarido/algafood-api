package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.CityRequest;
import com.algaworks.algafood.api.model.response.CityResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cities")
public interface CityControllerDocumentation {

    @ApiOperation("Lists all the cities that are available to use")
    ResponseEntity<List<CityResponse>> list();

    @ApiOperation("Finds a city by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "City not found", response = Error.class)
    })
    ResponseEntity<CityResponse> find(@ApiParam(value = "City identifier", example = "1") Long id);

    @ApiOperation("Registers a new city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City registered successfully")
    })
    ResponseEntity<CityResponse> create(@ApiParam(value = "Request body with data about the new city") CityRequest cityRequest);

    @ApiOperation("Updates an existing city with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated successfully"),
            @ApiResponse(code = 404, message = "City not found", response = Error.class)
    })
    ResponseEntity<CityResponse> update(@ApiParam(value = "City identifier", example = "1") Long id,
                                               @ApiParam(value = "Request body with updated data about the existing city") CityRequest cityRequest);

    @ApiOperation("Deletes an existing city by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City removed successfully"),
            @ApiResponse(code = 404, message = "City not found", response = Error.class)
    })
    ResponseEntity<Void> delete(@ApiParam(value = "City identifier", example = "1") Long id);
}
