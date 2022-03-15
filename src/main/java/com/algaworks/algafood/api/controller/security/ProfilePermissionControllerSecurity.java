package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.response.PermissionResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfilePermissionControllerSecurity {

    @HasAuthority.User.Permission.Query
    ResponseEntity<List<PermissionResponse>> list(Long profileId);

    @HasAuthority.User.Permission.Manage
    ResponseEntity<Void> addPermission(Long profileId, Long permissionId);

    @HasAuthority.User.Permission.Manage
    ResponseEntity<Void> removePermission(Long profileId, Long permissionId);
}
