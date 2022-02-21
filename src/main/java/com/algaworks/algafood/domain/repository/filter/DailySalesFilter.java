package com.algaworks.algafood.domain.repository.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
public class DailySalesFilter {

    @ApiModelProperty(example = "1")
    private Long restaurantId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(example = "2022-02-21T00:03:09.5106695Z")
    private OffsetDateTime createdStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(example = "2022-03-21T00:03:09.5106695Z")
    private OffsetDateTime createdEnd;
}
