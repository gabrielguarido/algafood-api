package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CityIdRequest {

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;
}
