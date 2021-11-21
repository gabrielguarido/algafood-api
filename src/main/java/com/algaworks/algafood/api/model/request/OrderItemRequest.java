package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class OrderItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    @PositiveOrZero
    private Integer amount;

    private String observation;
}
