package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.PasswordRequest;
import com.algaworks.algafood.api.model.request.UserRequest;
import com.algaworks.algafood.api.model.request.UserWithPasswordRequest;
import com.algaworks.algafood.api.model.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Users")
public interface UserControllerDocumentation {

    @ApiOperation("Lists all the users that are available")
    ResponseEntity<List<UserResponse>> list();

    @ApiOperation("Finds a user by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "User not found", response = Error.class)
    })
    ResponseEntity<UserResponse> find(@ApiParam(value = "User identifier", example = "1") Long id);

    @ApiOperation("Registers a new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User registered successfully")
    })
    ResponseEntity<UserResponse> create(@ApiParam(value = "Request body with data about the new user") UserWithPasswordRequest userWithPasswordRequest);

    @ApiOperation("Updates an existing user with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 404, message = "User not found", response = Error.class)
    })
    ResponseEntity<UserResponse> update(@ApiParam(value = "User identifier", example = "1") Long id,
                                        @ApiParam(value = "Request body with updated data about the existing user") UserRequest userRequest);

    @ApiOperation("Updates the password of an existing user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Password updated successfully"),
            @ApiResponse(code = 404, message = "User not found", response = Error.class)
    })
    ResponseEntity<Void> updatePassword(@ApiParam(value = "User identifier", example = "1") Long id,
                                        @ApiParam(value = "Request body with the new password") PasswordRequest passwordRequest);
}
