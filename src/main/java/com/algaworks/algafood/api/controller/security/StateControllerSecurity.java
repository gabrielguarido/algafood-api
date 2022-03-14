package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.StateRequest;
import com.algaworks.algafood.api.model.response.StateResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StateControllerSecurity {

    @HasAuthority.State.Query
    ResponseEntity<List<StateResponse>> list();

    @HasAuthority.State.Query
    ResponseEntity<StateResponse> find(Long id);

    @HasAuthority.State.Manage
    ResponseEntity<StateResponse> create(StateRequest stateRequest);

    @HasAuthority.State.Manage
    ResponseEntity<StateResponse> update(Long id, StateRequest stateRequest);

    @HasAuthority.State.Manage
    ResponseEntity<Void> delete(Long id);
}
