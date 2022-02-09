package com.algaworks.algafood.domain.service.query;

import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;

import java.util.List;

public interface DailySalesQueryService {

    List<DailySales> findDailySales(DailySalesFilter filter, String timeOffset);
}
