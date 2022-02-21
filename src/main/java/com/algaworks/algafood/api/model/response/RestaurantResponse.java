package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestaurantResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "McDonald's")
    private String name;

    @ApiModelProperty(example = "5.90")
    private BigDecimal deliveryFee;

    private CategoryResponse category;

    @ApiModelProperty(example = "true")
    private Boolean active;

    @ApiModelProperty(example = "true")
    private Boolean open;

    private AddressResponse address;
}
