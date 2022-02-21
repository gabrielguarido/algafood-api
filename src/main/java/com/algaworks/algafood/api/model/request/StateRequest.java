package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StateRequest {

    @NotBlank
    @ApiModelProperty(example = "São Paulo")
    private String name;
}
