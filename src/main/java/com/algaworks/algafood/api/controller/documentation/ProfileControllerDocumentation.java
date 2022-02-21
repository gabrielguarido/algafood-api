package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.ProfileRequest;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Profiles")
public interface ProfileControllerDocumentation {

    @ApiOperation("Lists all the profiles that are available to use")
    ResponseEntity<List<ProfileResponse>> list();

    @ApiOperation("Finds a profile by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Profile not found", response = Error.class)
    })
    ResponseEntity<ProfileResponse> find(Long id);

    @ApiOperation("Registers a new profile")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Profile registered successfully")
    })
    ResponseEntity<ProfileResponse> create(ProfileRequest profileRequest);

    @ApiOperation("Updates an existing profile with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Profile updated successfully"),
            @ApiResponse(code = 404, message = "Profile not found", response = Error.class)
    })
    ResponseEntity<ProfileResponse> update(Long id, ProfileRequest profileRequest);

    @ApiOperation("Deletes an existing profile by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Profile removed successfully"),
            @ApiResponse(code = 404, message = "Profile not found", response = Error.class)
    })
    ResponseEntity<Void> delete(Long id);
}
