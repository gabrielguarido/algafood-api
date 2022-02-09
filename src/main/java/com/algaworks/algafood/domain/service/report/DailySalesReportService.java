package com.algaworks.algafood.domain.service.report;

import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;

public interface DailySalesReportService {

    byte[] issueDailySales(DailySalesFilter filter, String timeOffset);
}
