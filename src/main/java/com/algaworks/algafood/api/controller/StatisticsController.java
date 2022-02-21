package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.StatisticsControllerDocumentation;
import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;
import com.algaworks.algafood.domain.service.query.DailySalesQueryService;
import com.algaworks.algafood.domain.service.report.DailySalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController implements StatisticsControllerDocumentation {

    private final DailySalesQueryService dailySalesQueryService;

    private final DailySalesReportService dailySalesReportService;

    @Autowired
    public StatisticsController(DailySalesQueryService dailySalesQueryService,
                                DailySalesReportService dailySalesReportService) {
        this.dailySalesQueryService = dailySalesQueryService;
        this.dailySalesReportService = dailySalesReportService;
    }

    @GetMapping(value = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DailySales>> findDailySales(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return ResponseEntity.ok(dailySalesQueryService.findDailySales(filter, timeOffset));
    }

    @GetMapping(value = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> findDailySalesPdf(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] pdfBytes = dailySalesReportService.issueDailySales(filter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(pdfBytes);
    }
}
