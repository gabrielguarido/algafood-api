package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Users")
public interface UserProfileControllerDocumentation {

    @ApiOperation("Lists all the profiles that a specific user has")
    ResponseEntity<List<ProfileResponse>> list(@ApiParam(value = "User identifier", example = "1") Long userId);

    @ApiOperation("Associates a user with a specific profile")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association successful"),
            @ApiResponse(code = 404, message = "Profile or user not found", response = Error.class)
    })
    ResponseEntity<Void> addProfile(@ApiParam(value = "User identifier", example = "1") Long userId,
                                    @ApiParam(value = "Profile identifier", example = "1") Long profileId);

    @ApiOperation("Disassociates a user with a specific profile")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Disassociation successful"),
            @ApiResponse(code = 404, message = "Profile or user not found", response = Error.class)
    })
    ResponseEntity<Void> removeProfile(@ApiParam(value = "User identifier", example = "1") Long userId,
                                       @ApiParam(value = "Profile identifier", example = "1") Long profileId);
}
