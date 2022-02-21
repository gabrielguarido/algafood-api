package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.request.PaymentMethodRequest;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Payment Methods")
public interface PaymentMethodControllerDocumentation {

    @ApiOperation("Lists all the payment methods that are available to use for a specific restaurant")
    ResponseEntity<List<PaymentMethodResponse>> list();

    @ApiOperation("Finds a payment method by its ID value")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid identifier"),
            @ApiResponse(code = 404, message = "Payment method not found", response = Error.class)
    })
    ResponseEntity<PaymentMethodResponse> find(Long id);

    @ApiOperation("Registers a new payment method for a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Payment method registered successfully")
    })
    ResponseEntity<PaymentMethodResponse> create(PaymentMethodRequest paymentMethodRequest);

    @ApiOperation("Deletes an existing payment method by its ID value")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Payment method removed successfully"),
            @ApiResponse(code = 404, message = "Payment method not found", response = Error.class)
    })
    ResponseEntity<Void> delete(Long id);
}
