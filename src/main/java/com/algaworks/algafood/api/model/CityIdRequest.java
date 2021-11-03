package com.algaworks.algafood.api.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CityIdRequest {

    @NotNull
    private Long id;
}
