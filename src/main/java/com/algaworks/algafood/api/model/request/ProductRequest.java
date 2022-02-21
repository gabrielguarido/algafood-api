package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank
    @ApiModelProperty(example = "Big Mac", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(example = "Double burger", required = true)
    private String description;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "33.50", required = true)
    private BigDecimal price;

    @NotNull
    @ApiModelProperty(example = "true", required = true)
    private Boolean active;
}
