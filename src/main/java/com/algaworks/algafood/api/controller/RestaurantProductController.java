package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.RestaurantProductControllerDocumentation;
import com.algaworks.algafood.api.controller.security.RestaurantProductControllerSecurity;
import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductPictureResponse;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "restaurant/{restaurantId}/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController implements RestaurantProductControllerDocumentation, RestaurantProductControllerSecurity {

    private final ProductService productService;

    @Autowired
    public RestaurantProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> list(@PathVariable Long restaurantId,
                                                      @RequestParam(required = false) boolean includeInactive) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(productService.list(restaurantId, includeInactive));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> find(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(productService.find(restaurantId, productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@PathVariable Long restaurantId,
                                                @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.save(restaurantId, productRequest));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long restaurantId, @PathVariable Long productId,
                                                  @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(restaurantId, productId, productRequest));
    }

    @PutMapping(value = "/{productId}/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePicture(@PathVariable Long restaurantId, @PathVariable Long productId,
                                              @Valid ProductPictureRequest request) throws IOException {
        productService.updatePicture(restaurantId, productId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{productId}/picture", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> retrievePicture(@PathVariable Long restaurantId, @PathVariable Long productId) {
        ProductPictureResponse response = productService.retrievePicture(restaurantId, productId);

        if (response.hasInputStream()) {
            return ResponseEntity
                    .ok()
                    .contentType(response.getMediaType())
                    .body(new InputStreamResource(response.getInputStream()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, response.getUrl())
                    .build();
        }
    }

    @DeleteMapping("/{productId}/picture")
    public ResponseEntity<Void> deletePicture(@PathVariable Long restaurantId, @PathVariable Long productId) {
        productService.deletePicture(restaurantId, productId);

        return ResponseEntity.noContent().build();
    }
}
