package com.algaworks.algafood.api.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class OrderModelResponse {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private String status;
    private OffsetDateTime created;
    private RestaurantModelResponse restaurant;
    private UserResponse client;
}
