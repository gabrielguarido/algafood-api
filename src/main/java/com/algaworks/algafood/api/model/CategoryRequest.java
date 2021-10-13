package com.algaworks.algafood.api.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryRequest {

    @NotBlank
    private String type;
}
