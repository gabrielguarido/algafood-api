package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.CategoryRequest;
import com.algaworks.algafood.api.model.response.CategoryResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

@Api(tags = "Categories")
public interface CategoryControllerSecurity {

    @HasAuthority.Category.Manage
    ResponseEntity<CategoryResponse> create(@ApiParam(value = "Request body with data about the new category") CategoryRequest categoryRequest);

    @HasAuthority.Category.Manage
    ResponseEntity<CategoryResponse> update(@ApiParam(value = "Category identifier", example = "1") Long id,
                                            @ApiParam(value = "Request body with updated data about the existing category") CategoryRequest categoryRequest);

    @HasAuthority.Category.Manage
    ResponseEntity<Void> delete(@ApiParam(value = "Category identifier", example = "1") Long id);
}
