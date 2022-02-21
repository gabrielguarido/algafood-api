package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RestaurantModelResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "McDonald's")
    private String name;
}
