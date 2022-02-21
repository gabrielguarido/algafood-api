package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CityRequest {

    @NotBlank
    @ApiModelProperty(example = "São Paulo", required = true)
    private String name;

    @Valid
    @NotNull
    private StateIdRequest state;
}
