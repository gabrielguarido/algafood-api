package com.algaworks.algafood.api.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CityRequest {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdRequest state;
}
