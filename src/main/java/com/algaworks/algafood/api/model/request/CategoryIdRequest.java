package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryIdRequest {

    @NotNull
    private Long id;
}
