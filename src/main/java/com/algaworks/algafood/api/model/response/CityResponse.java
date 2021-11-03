package com.algaworks.algafood.api.model.response;

import lombok.Data;

@Data
public class CityResponse {

    private Long id;
    private String name;
    private StateResponse state;
}
