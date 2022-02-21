package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileRequest {

    @NotBlank
    @ApiModelProperty(example = "Admin", required = true)
    private String name;
}
