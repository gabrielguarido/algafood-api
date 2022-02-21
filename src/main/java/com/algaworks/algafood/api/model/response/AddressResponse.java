package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddressResponse {

    @ApiModelProperty(example = "02332-136")
    private String zipCode;

    @ApiModelProperty(example = "Rua Maria Souza")
    private String addressDescription;

    @ApiModelProperty(example = "185")
    private String number;

    @ApiModelProperty(example = "Apartment 75 B")
    private String complement;

    @ApiModelProperty(example = "Penha")
    private String province;

    private CityModelResponse city;
}
