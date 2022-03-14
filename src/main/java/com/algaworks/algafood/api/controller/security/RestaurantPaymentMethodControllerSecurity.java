package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantPaymentMethodControllerSecurity {

    @HasAuthority.Restaurant.PaymentMethod.Query
    ResponseEntity<List<PaymentMethodResponse>> list(Long restaurantId);

    @HasAuthority.Restaurant.PaymentMethod.ManageOperation
    ResponseEntity<Void> add(Long restaurantId, Long paymentMethodId);

    @HasAuthority.Restaurant.PaymentMethod.ManageOperation
    ResponseEntity<Void> remove(Long restaurantId, Long paymentMethodId);
}
