package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class RestaurantRequest {

    @NotBlank
    @ApiModelProperty(example = "McDonald's", required = true)
    private String name;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "5.90", required = true)
    private BigDecimal deliveryFee;

    @Valid
    @NotNull
    private CategoryIdRequest category;

    @Valid
    @NotNull
    private AddressRequest address;
}
