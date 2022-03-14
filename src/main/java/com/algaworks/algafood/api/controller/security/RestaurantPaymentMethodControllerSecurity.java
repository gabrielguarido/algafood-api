package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantPaymentMethodControllerSecurity {

    @HasAuthority.Restaurant.PaymentMethod.Query
    ResponseEntity<List<PaymentMethodResponse>> list(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId);

    @HasAuthority.Restaurant.PaymentMethod.Manage
    ResponseEntity<Void> add(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                             @ApiParam(value = "Payment method identifier", example = "1") Long paymentMethodId);

    @HasAuthority.Restaurant.PaymentMethod.Manage
    ResponseEntity<Void> remove(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                @ApiParam(value = "Payment method identifier", example = "1") Long paymentMethodId);
}
