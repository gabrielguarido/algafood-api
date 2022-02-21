package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurants")
public interface RestaurantResponsibleUserControllerDocumentation {

    @ApiOperation("Lists all the users that are responsible for managing a specific restaurant")
    ResponseEntity<List<UserResponse>> list(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId);

    @ApiOperation("Associates a responsible user with a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association successful"),
            @ApiResponse(code = 404, message = "Restaurant or responsible user not found", response = Error.class)
    })
    ResponseEntity<Void> add(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                             @ApiParam(value = "Responsible user identifier", example = "1") Long userId);

    @ApiOperation("Disassociates a responsible user with a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Disassociation successful"),
            @ApiResponse(code = 404, message = "Restaurant or responsible user not found", response = Error.class)
    })
    ResponseEntity<Void> remove(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                @ApiParam(value = "Responsible user identifier", example = "1") Long userId);
}
