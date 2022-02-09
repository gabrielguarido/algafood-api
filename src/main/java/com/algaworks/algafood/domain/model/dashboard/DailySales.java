package com.algaworks.algafood.domain.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class DailySales {

    private Date date;
    private long totalSales;
    private BigDecimal totalBilled;
}
