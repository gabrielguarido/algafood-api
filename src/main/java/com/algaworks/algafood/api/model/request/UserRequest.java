package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    @NotBlank
    @ApiModelProperty(example = "Gabriel Oliveira", required = true)
    private String name;

    @Email
    @NotBlank
    @ApiModelProperty(example = "gabriel@gmail.com", required = true)
    private String email;
}
