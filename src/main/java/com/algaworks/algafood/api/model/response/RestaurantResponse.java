package com.algaworks.algafood.api.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantResponse {

    private Long id;
    private String name;
    private BigDecimal deliveryFee;
    private CategoryResponse category;
    private Boolean active;
    private Boolean open;
    private AddressResponse address;
}
