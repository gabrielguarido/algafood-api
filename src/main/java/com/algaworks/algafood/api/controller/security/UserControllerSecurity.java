package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.PasswordRequest;
import com.algaworks.algafood.api.model.request.UserRequest;
import com.algaworks.algafood.api.model.request.UserWithPasswordRequest;
import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserControllerSecurity {

    @HasAuthority.User.Query
    ResponseEntity<List<UserResponse>> list();

    @HasAuthority.User.Query
    ResponseEntity<UserResponse> find(Long id);

    @HasAuthority.User.Manage
    ResponseEntity<UserResponse> create(UserWithPasswordRequest userWithPasswordRequest);

    @HasAuthority.User.Update
    ResponseEntity<UserResponse> update(Long id, UserRequest userRequest);

    @HasAuthority.User.Update
    ResponseEntity<Void> updatePassword(Long id, PasswordRequest passwordRequest);
}
