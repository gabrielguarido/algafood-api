package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.domain.model.dashboard.DailySales;
import com.algaworks.algafood.domain.repository.filter.DailySalesFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Statistics")
public interface StatisticsControllerDocumentation {

    @ApiOperation("Retrieves statistics about daily sales in a JSON format")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "Restaurant identifier", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "createdStart", value = "Order creation date/time - Initial value", example = "2022-02-21T00:03:09.5106695Z", dataType = "date-time"),
            @ApiImplicitParam(name = "createdEnd", value = "Order creation date/time - Final value", example = "2022-03-21T00:03:09.5106695Z", dataType = "date-time")
    })
    ResponseEntity<List<DailySales>> findDailySales(@ApiParam(value = "Search filter") DailySalesFilter filter,
                                                    @ApiParam(value = "Time offset to be considered in the query relative to UTC", defaultValue = "+00:00") String timeOffset);

    @ApiOperation("Retrieves statistics about daily sales in a PDF format")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "Restaurant identifier", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "createdStart", value = "Order creation date/time - Initial value", example = "2022-02-21T00:03:09.5106695Z", dataType = "date-time"),
            @ApiImplicitParam(name = "createdEnd", value = "Order creation date/time - Final value", example = "2022-03-21T00:03:09.5106695Z", dataType = "date-time")
    })
    ResponseEntity<byte[]> findDailySalesPdf(@ApiParam(value = "Search filter") DailySalesFilter filter,
                                             @ApiParam(value = "Time offset to be considered in the query relative to UTC", defaultValue = "+00:00") String timeOffset);
}
