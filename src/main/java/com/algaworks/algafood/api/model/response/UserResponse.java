package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gabriel Oliveira")
    private String name;

    @ApiModelProperty(example = "gabriel@gmail.com")
    private String email;
}
