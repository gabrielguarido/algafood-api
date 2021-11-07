package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "restaurant/{restaurantId}/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController {

    private final ProductService productService;

    @Autowired
    public RestaurantProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> list(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(productService.list(restaurantId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> find(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return ResponseEntity.ok(productService.find(restaurantId, productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@PathVariable Long restaurantId, @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.save(restaurantId, productRequest));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(restaurantId, productId, productRequest));
    }
}
