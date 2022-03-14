package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.CategoryRequest;
import com.algaworks.algafood.api.model.response.CategoryResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryControllerSecurity {

    @HasAuthority.Category.Query
    ResponseEntity<Page<CategoryResponse>> list(Pageable pageable);

    @HasAuthority.Category.Query
    ResponseEntity<CategoryResponse> find(@ApiParam(value = "Category identifier", example = "1") Long id);

    @HasAuthority.Category.Query
    ResponseEntity<CategoryResponse> findByType(@ApiParam(value = "Category type", example = "Burger") String type);

    @HasAuthority.Category.Query
    ResponseEntity<Boolean> existsByType(@ApiParam(value = "Category type", example = "Burger") String type);

    @HasAuthority.Category.Query
    ResponseEntity<CategoryResponse> findFirst();

    @HasAuthority.Category.Manage
    ResponseEntity<CategoryResponse> create(@ApiParam(value = "Request body with data about the new category") CategoryRequest categoryRequest);

    @HasAuthority.Category.Manage
    ResponseEntity<CategoryResponse> update(@ApiParam(value = "Category identifier", example = "1") Long id,
                                            @ApiParam(value = "Request body with updated data about the existing category") CategoryRequest categoryRequest);

    @HasAuthority.Category.Manage
    ResponseEntity<Void> delete(@ApiParam(value = "Category identifier", example = "1") Long id);
}
