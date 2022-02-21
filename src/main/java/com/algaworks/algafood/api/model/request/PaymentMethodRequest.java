package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentMethodRequest {

    @NotBlank
    @ApiModelProperty(example = "CREDIT CARD", required = true)
    private String method;
}
