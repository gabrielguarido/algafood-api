package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@Api(tags = "Products")
public interface RestaurantProductControllerDocumentation {

    @ApiOperation("Lists all the products that are available for a specific restaurant, can also include inactive products")
    ResponseEntity<List<ProductResponse>> list(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                               @ApiParam(value = "Include inactive products", example = "false") boolean includeInactive);

    @ApiOperation("Finds a product by its ID value for a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = Error.class)
    })
    ResponseEntity<ProductResponse> find(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                         @ApiParam(value = "Product identifier", example = "1") Long productId);

    @ApiOperation("Registers a new product for a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product registered successfully"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = Error.class)
    })
    ResponseEntity<ProductResponse> save(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                         ProductRequest productRequest);

    @ApiOperation("Updates an existing product with new values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product updated successfully"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = Error.class)
    })
    ResponseEntity<ProductResponse> update(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                           @ApiParam(value = "Product identifier", example = "1") Long productId,
                                           @ApiParam(value = "Request body with the picture file") ProductRequest productRequest);

    @ApiOperation("Updates an existing product picture with a new one")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product picture updated successfully"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = Error.class)
    })
    ResponseEntity<Void> updatePicture(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                       @ApiParam(value = "Product identifier", example = "1") Long productId,
                                       @ApiParam(value = "Request body with the new picture file") ProductPictureRequest request) throws IOException;

    @ApiOperation("Retrieves a product picture by its ID value for a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = Error.class)
    })
    ResponseEntity<?> retrievePicture(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                      @ApiParam(value = "Product identifier", example = "1") Long productId);

    @ApiOperation("Deletes an existing product picture by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product picture removed successfully"),
            @ApiResponse(code = 404, message = "Restaurant or product not found", response = Error.class)
    })
    ResponseEntity<Void> deletePicture(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                       @ApiParam(value = "Product identifier", example = "1") Long productId);
}
