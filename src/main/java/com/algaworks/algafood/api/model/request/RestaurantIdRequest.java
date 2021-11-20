package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RestaurantIdRequest {

    @NotNull
    private Long id;
}
