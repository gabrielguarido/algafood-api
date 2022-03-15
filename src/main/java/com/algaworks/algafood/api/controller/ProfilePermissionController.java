package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.ProfilePermissionControllerDocumentation;
import com.algaworks.algafood.api.controller.security.ProfilePermissionControllerSecurity;
import com.algaworks.algafood.api.model.response.PermissionResponse;
import com.algaworks.algafood.domain.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "profile/{profileId}/permission", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfilePermissionController implements ProfilePermissionControllerDocumentation, ProfilePermissionControllerSecurity {

    private final ProfileService profileService;

    @Autowired
    public ProfilePermissionController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> list(@PathVariable Long profileId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(profileService.listPermissions(profileId));
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Void> addPermission(@PathVariable Long profileId, @PathVariable Long permissionId) {
        profileService.addPermission(profileId, permissionId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> removePermission(@PathVariable Long profileId, @PathVariable Long permissionId) {
        profileService.removePermission(profileId, permissionId);

        return ResponseEntity.noContent().build();
    }
}
