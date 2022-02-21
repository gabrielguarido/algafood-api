package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressRequest {

    @NotBlank
    @ApiModelProperty(example = "02332-136", required = true)
    private String zipCode;

    @NotBlank
    @ApiModelProperty(example = "Rua Maria Souza", required = true)
    private String addressDescription;

    @NotBlank
    @ApiModelProperty(example = "185", required = true)
    private String number;

    @NotBlank
    @ApiModelProperty(example = "Apartment 75 B", required = true)
    private String complement;

    @NotBlank
    @ApiModelProperty(example = "Penha", required = true)
    private String province;

    @Valid
    @NotNull
    @ApiModelProperty(example = "São Paulo", required = true)
    private CityIdRequest city;
}
