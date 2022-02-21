package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.OrderRequest;
import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Api(tags = "Orders")
public interface OrderControllerDocumentation {

    @ApiOperation("Searches for specific orders using a search filter")
    ResponseEntity<Page<OrderModelResponse>> search(@ApiParam(value = "Search filter") OrderFilter filter, Pageable pageable);

    @ApiOperation("Finds an order by its external key")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid external key"),
            @ApiResponse(code = 404, message = "Order not found", response = Error.class)
    })
    ResponseEntity<OrderResponse> find(@ApiParam(value = "Order identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID externalKey);

    @ApiOperation("Issues a new order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order issued successfully")
    })
    ResponseEntity<OrderResponse> issueOrder(@ApiParam(value = "Request body with data about the new order") OrderRequest orderRequest);

    @ApiOperation("Confirms an existing order by its external key")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order confirmed successfully"),
            @ApiResponse(code = 400, message = "Invalid external key"),
            @ApiResponse(code = 404, message = "Order not found", response = Error.class)
    })
    ResponseEntity<Void> confirmOrder(@ApiParam(value = "Order identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID externalKey);

    @ApiOperation("Delivers an existing order by its external key")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order delivered successfully"),
            @ApiResponse(code = 400, message = "Invalid external key"),
            @ApiResponse(code = 404, message = "Order not found", response = Error.class)
    })
    ResponseEntity<Void> deliverOrder(@ApiParam(value = "Order identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID externalKey);

    @ApiOperation("Cancels an existing order by its external key")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order cancelled successfully"),
            @ApiResponse(code = 400, message = "Invalid external key"),
            @ApiResponse(code = 404, message = "Order not found", response = Error.class)
    })
    ResponseEntity<Void> cancelOrder(@ApiParam(value = "Order identifier", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID externalKey);
}
