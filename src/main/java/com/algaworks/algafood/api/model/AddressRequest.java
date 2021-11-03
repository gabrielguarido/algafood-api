package com.algaworks.algafood.api.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressRequest {

    @NotBlank
    private String zipCode;

    @NotBlank
    private String addressDescription;

    @NotBlank
    private String number;

    @NotBlank
    private String complement;

    @NotBlank
    private String province;

    @Valid
    @NotNull
    private CityIdRequest city;
}
