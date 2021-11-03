package com.algaworks.algafood.api.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentMethodRequest {

    @NotBlank
    private String method;
}
