package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;
import com.algaworks.algafood.domain.repository.query.DailySalesServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    private final DailySalesServiceQuery dailySalesServiceQuery;

    @Autowired
    public StatisticsController(DailySalesServiceQuery dailySalesServiceQuery) {
        this.dailySalesServiceQuery = dailySalesServiceQuery;
    }

    @GetMapping("/daily-sales")
    public List<DailySales> findDailySales(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return dailySalesServiceQuery.findDailySales(filter, timeOffset);
    }
}
