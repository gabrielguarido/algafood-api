package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface RestaurantProductControllerSecurity {

    @HasAuthority.Restaurant.Product.Query
    ResponseEntity<List<ProductResponse>> list(Long restaurantId, boolean includeInactive);

    @HasAuthority.Restaurant.Product.Query
    ResponseEntity<ProductResponse> find(Long restaurantId, Long productId);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<ProductResponse> save(Long restaurantId, ProductRequest productRequest);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<ProductResponse> update(Long restaurantId, Long productId, ProductRequest productRequest);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<Void> updatePicture(Long restaurantId, Long productId, ProductPictureRequest request) throws IOException;

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<?> retrievePicture(Long restaurantId, Long productId);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<Void> deletePicture(Long restaurantId, Long productId);
}
