package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.ProfileRequest;
import com.algaworks.algafood.api.model.response.ProfileResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfileControllerSecurity {

    @HasAuthority.User.Profile.Query
    ResponseEntity<List<ProfileResponse>> list();

    @HasAuthority.User.Profile.Query
    ResponseEntity<ProfileResponse> find(Long id);

    @HasAuthority.User.Profile.Manage
    ResponseEntity<ProfileResponse> create(ProfileRequest profileRequest);

    @HasAuthority.User.Profile.Manage
    ResponseEntity<ProfileResponse> update(Long id, ProfileRequest profileRequest);

    @HasAuthority.User.Profile.Manage
    ResponseEntity<Void> delete(Long id);
}
