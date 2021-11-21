package com.algaworks.algafood.api.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer amount;
    private String observation;
}
