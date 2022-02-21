package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserWithPasswordRequest extends UserRequest {

    @NotBlank
    @Size(min = 8, max = 15)
    @ApiModelProperty(example = "my_password", required = true)
    private String password;
}
