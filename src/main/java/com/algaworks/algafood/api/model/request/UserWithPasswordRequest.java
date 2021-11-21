package com.algaworks.algafood.api.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserWithPasswordRequest extends UserRequest {

    @Size(min = 8, max = 15)
    @NotBlank
    private String password;
}
