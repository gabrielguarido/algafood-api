package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.PaymentMethodRequest;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentMethodControllerSecurity {

    @HasAuthority.Restaurant.PaymentMethod.Query
    ResponseEntity<List<PaymentMethodResponse>> list();

    @HasAuthority.Restaurant.PaymentMethod.Query
    ResponseEntity<PaymentMethodResponse> find(Long id);

    @HasAuthority.Restaurant.PaymentMethod.Manage
    ResponseEntity<PaymentMethodResponse> create(PaymentMethodRequest paymentMethodRequest);

    @HasAuthority.Restaurant.PaymentMethod.Manage
    ResponseEntity<Void> delete(Long id);
}
