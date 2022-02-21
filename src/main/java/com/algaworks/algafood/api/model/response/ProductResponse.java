package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Big Mac")
    private String name;

    @ApiModelProperty(example = "Double burger")
    private String description;

    @ApiModelProperty(example = "33.50")
    private BigDecimal price;

    @ApiModelProperty(example = "true")
    private Boolean active;
}
