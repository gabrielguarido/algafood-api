package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StateResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "São Paulo")
    private String name;
}
