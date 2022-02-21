package com.algaworks.algafood.domain.model.dashboard;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class DailySales {

    @ApiModelProperty(example = "2022-02-21T00:03:09.5106695Z")
    private Date date;

    @ApiModelProperty(example = "25")
    private long totalSales;

    @ApiModelProperty(example = "55549.87")
    private BigDecimal totalBilled;
}
