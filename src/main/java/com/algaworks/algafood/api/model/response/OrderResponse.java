package com.algaworks.algafood.api.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponse {

    private String externalKey;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private String status;
    private OffsetDateTime created;
    private OffsetDateTime confirmed;
    private OffsetDateTime cancelled;
    private OffsetDateTime delivered;
    private RestaurantModelResponse restaurant;
    private UserResponse client;
    private PaymentMethodResponse paymentMethod;
    private AddressResponse address;
    private List<OrderItemResponse> items;
}
