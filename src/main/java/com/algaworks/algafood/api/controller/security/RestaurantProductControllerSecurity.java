package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface RestaurantProductControllerSecurity {

    @HasAuthority.Restaurant.Product.Query
    ResponseEntity<List<ProductResponse>> list(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                               @ApiParam(value = "Include inactive products", example = "false") boolean includeInactive);

    @HasAuthority.Restaurant.Product.Query
    ResponseEntity<ProductResponse> find(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                         @ApiParam(value = "Product identifier", example = "1") Long productId);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<ProductResponse> save(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                         ProductRequest productRequest);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<ProductResponse> update(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                           @ApiParam(value = "Product identifier", example = "1") Long productId,
                                           @ApiParam(value = "Request body with the picture file") ProductRequest productRequest);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<Void> updatePicture(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                       @ApiParam(value = "Product identifier", example = "1") Long productId,
                                       @ApiParam(value = "Request body with the new picture file") ProductPictureRequest request) throws IOException;

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<?> retrievePicture(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                      @ApiParam(value = "Product identifier", example = "1") Long productId);

    @HasAuthority.Restaurant.Product.Manage
    ResponseEntity<Void> deletePicture(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                       @ApiParam(value = "Product identifier", example = "1") Long productId);
}
