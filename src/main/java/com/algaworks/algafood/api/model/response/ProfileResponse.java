package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProfileResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Admin")
    private String name;
}
