package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "restaurant/{restaurantId}/payment-method", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantPaymentMethodController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodResponse>> list(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.listPaymentMethods(restaurantId));
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<Void> add(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantService.addPaymentMethod(restaurantId, paymentMethodId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<Void> remove(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantService.removePaymentMethod(restaurantId, paymentMethodId);

        return ResponseEntity.noContent().build();
    }
}
