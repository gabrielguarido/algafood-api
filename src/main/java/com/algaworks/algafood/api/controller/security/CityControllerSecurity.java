package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.CityRequest;
import com.algaworks.algafood.api.model.response.CityResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CityControllerSecurity {

    @HasAuthority.City.Query
    ResponseEntity<List<CityResponse>> list();

    @HasAuthority.City.Query
    ResponseEntity<CityResponse> find(Long id);

    @HasAuthority.City.Manage
    ResponseEntity<CityResponse> create(CityRequest cityRequest);

    @HasAuthority.City.Manage
    ResponseEntity<CityResponse> update(Long id, CityRequest cityRequest);

    @HasAuthority.City.Manage
    ResponseEntity<Void> delete(Long id);
}
