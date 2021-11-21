package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderRequest {

    @Valid
    @NotNull
    private RestaurantIdRequest restaurant;

    @Valid
    @NotNull
    private AddressRequest deliveryAddress;

    @Valid
    @NotNull
    private PaymentMethodIdRequest paymentMethod;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<OrderItemRequest> items;
}
