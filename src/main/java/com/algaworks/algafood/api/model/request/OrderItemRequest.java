package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class OrderItemRequest {

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long productId;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "2", required = true)
    private Integer amount;

    @ApiModelProperty(example = "No onion, no mayo", required = true)
    private String observation;
}
