package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;
import com.algaworks.algafood.domain.service.query.DailySalesQueryService;
import com.algaworks.algafood.domain.service.report.DailySalesReportService;
import com.algaworks.algafood.infrastructure.exception.ReportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class DailySalesReportServiceImpl implements DailySalesReportService {

    private static final String RESOURCE_FILE_PATH = "/reports/daily-sales.jasper";
    private static final Locale LOCALE = new Locale("pt", "BR");

    private final DailySalesQueryService dailySalesQueryService;

    @Autowired
    public DailySalesReportServiceImpl(DailySalesQueryService dailySalesQueryService) {
        this.dailySalesQueryService = dailySalesQueryService;
    }

    @Override
    public byte[] issueDailySales(DailySalesFilter filter, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream(RESOURCE_FILE_PATH);

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", LOCALE);

            var dailySales = dailySalesQueryService.findDailySales(filter, timeOffset);

            var dataSource = new JRBeanCollectionDataSource(dailySales);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("daily-sales");
        }
    }
}
