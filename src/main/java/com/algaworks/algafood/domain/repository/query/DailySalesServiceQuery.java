package com.algaworks.algafood.domain.repository.query;

import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;

import java.util.List;

public interface DailySalesServiceQuery {

    List<DailySales> findDailySales(DailySalesFilter filter);
}
