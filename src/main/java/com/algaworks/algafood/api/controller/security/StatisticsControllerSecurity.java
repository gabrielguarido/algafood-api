package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.core.security.annotation.HasAuthority;
import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StatisticsControllerSecurity {

    @HasAuthority.Statistics.Query
    ResponseEntity<List<DailySales>> findDailySales(DailySalesFilter filter, String timeOffset);

    @HasAuthority.Statistics.Query
    ResponseEntity<byte[]> findDailySalesPdf(DailySalesFilter filter, String timeOffset);
}
