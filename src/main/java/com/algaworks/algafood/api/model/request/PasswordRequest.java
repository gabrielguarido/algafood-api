package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String repeatPassword;
}
