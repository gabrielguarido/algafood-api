package com.algaworks.algafood.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantResponse {

    private Long id;
    private String name;
    private BigDecimal deliveryFee;
    private Long categoryId;
}
