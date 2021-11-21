package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StateRequest {

    @NotBlank
    private String name;
}
