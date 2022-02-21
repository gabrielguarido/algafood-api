package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class OrderModelResponse {

    @ApiModelProperty(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID externalKey;

    @ApiModelProperty(example = "60.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "3.0")
    private BigDecimal deliveryFee;

    @ApiModelProperty(example = "63.90")
    private BigDecimal totalPrice;

    @ApiModelProperty(example = "CREATED")
    private String status;

    @ApiModelProperty(example = "2022-02-21T00:03:09.5106695Z")
    private OffsetDateTime created;

    private RestaurantModelResponse restaurant;

    private UserResponse client;
}
