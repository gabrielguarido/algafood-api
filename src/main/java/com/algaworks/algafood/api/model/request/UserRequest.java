package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}
