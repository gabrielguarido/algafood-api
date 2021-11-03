package com.algaworks.algafood.api.model.response;

import lombok.Data;

@Data
public class AddressResponse {

    private String zipCode;
    private String addressDescription;
    private String number;
    private String complement;
    private String province;
    private CityModelResponse city;
}
