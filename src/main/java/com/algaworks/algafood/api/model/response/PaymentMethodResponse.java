package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentMethodResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CREDIT CARD")
    private String method;
}
