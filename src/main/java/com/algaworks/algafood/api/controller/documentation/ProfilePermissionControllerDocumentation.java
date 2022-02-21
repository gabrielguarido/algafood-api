package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.response.PermissionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Profiles")
public interface ProfilePermissionControllerDocumentation {

    @ApiOperation("Lists all the permissions that a specific profile has")
    ResponseEntity<List<PermissionResponse>> list(@ApiParam(value = "Profile identifier", example = "1") Long profileId);

    @ApiOperation("Associates a permission with a specific profile")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association successful"),
            @ApiResponse(code = 404, message = "Profile or permission not found", response = Error.class)
    })
    ResponseEntity<Void> addPermission(@ApiParam(value = "Profile identifier", example = "1") Long profileId,
                                       @ApiParam(value = "Permission identifier", example = "1") Long permissionId);

    @ApiOperation("Disassociates a permission with a specific profile")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Disassociation successful"),
            @ApiResponse(code = 404, message = "Profile or permission not found", response = Error.class)
    })
    ResponseEntity<Void> removePermission(@ApiParam(value = "Profile identifier", example = "1") Long profileId,
                                          @ApiParam(value = "Permission identifier", example = "1") Long permissionId);
}
