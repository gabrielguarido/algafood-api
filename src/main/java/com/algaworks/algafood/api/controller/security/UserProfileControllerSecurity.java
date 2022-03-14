package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserProfileControllerSecurity {

    @HasAuthority.User.Profile.Query
    ResponseEntity<List<ProfileResponse>> list(Long userId);

    @HasAuthority.User.Profile.Manage
    ResponseEntity<Void> addProfile(Long userId, Long profileId);

    @HasAuthority.User.Profile.Manage
    ResponseEntity<Void> removeProfile(Long userId, Long profileId);
}
