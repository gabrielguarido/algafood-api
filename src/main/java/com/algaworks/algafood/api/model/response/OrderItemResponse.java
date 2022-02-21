package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    @ApiModelProperty(example = "1")
    private Long productId;

    @ApiModelProperty(example = "Big Mac")
    private String productName;

    @ApiModelProperty(example = "30.50")
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "61.0")
    private BigDecimal totalPrice;

    @ApiModelProperty(example = "2")
    private Integer amount;

    @ApiModelProperty(example = "No onion, no mayo")
    private String observation;
}
