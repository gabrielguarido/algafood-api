package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.StateRequest;
import com.algaworks.algafood.api.model.response.StateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "States")
public interface StateControllerDocumentation {

    @ApiOperation("Lists all the states that are available to use")
    ResponseEntity<List<StateResponse>> list();

    @ApiOperation("Finds a state by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "State not found", response = Error.class)
    })
    ResponseEntity<StateResponse> find(@ApiParam(value = "State identifier", example = "1") Long id);

    @ApiOperation("Registers a new state")
    @ApiResponses({
            @ApiResponse(code = 201, message = "State registered successfully")
    })
    ResponseEntity<StateResponse> create(@ApiParam(value = "Request body with data about the new state") StateRequest stateRequest);

    @ApiOperation("Updates an existing state with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "State updated successfully"),
            @ApiResponse(code = 404, message = "State not found", response = Error.class)
    })
    ResponseEntity<StateResponse> update(@ApiParam(value = "City identifier", example = "1") Long id,
                                         @ApiParam(value = "Request body with updated data about the existing state") StateRequest stateRequest);

    @ApiOperation("Deletes an existing state by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "State removed successfully"),
            @ApiResponse(code = 404, message = "State not found", response = Error.class)
    })
    ResponseEntity<Void> delete(@ApiParam(value = "City identifier", example = "1") Long id);
}
