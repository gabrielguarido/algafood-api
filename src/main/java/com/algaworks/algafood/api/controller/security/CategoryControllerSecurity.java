package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.CategoryRequest;
import com.algaworks.algafood.api.model.response.CategoryResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryControllerSecurity {

    @HasAuthority.Category.Query
    ResponseEntity<Page<CategoryResponse>> list(Pageable pageable);

    @HasAuthority.Category.Query
    ResponseEntity<CategoryResponse> find(Long id);

    @HasAuthority.Category.Query
    ResponseEntity<CategoryResponse> findByType(String type);

    @HasAuthority.Category.Query
    ResponseEntity<Boolean> existsByType(String type);

    @HasAuthority.Category.Query
    ResponseEntity<CategoryResponse> findFirst();

    @HasAuthority.Category.Manage
    ResponseEntity<CategoryResponse> create(CategoryRequest categoryRequest);

    @HasAuthority.Category.Manage
    ResponseEntity<CategoryResponse> update(Long id, CategoryRequest categoryRequest);

    @HasAuthority.Category.Manage
    ResponseEntity<Void> delete(Long id);
}
