package com.algaworks.algafood.api.controller.documentation;

import com.algaworks.algafood.api.exception.Error;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurants")
public interface RestaurantPaymentMethodControllerDocumentation {

    @ApiOperation("Lists all the payment methods that are available to use for a specific restaurant")
    ResponseEntity<List<PaymentMethodResponse>> list(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId);

    @ApiOperation("Associates a payment method with a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association successful"),
            @ApiResponse(code = 404, message = "Restaurant or payment method not found", response = Error.class)
    })
    ResponseEntity<Void> add(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                             @ApiParam(value = "Payment method identifier", example = "1") Long paymentMethodId);

    @ApiOperation("Disassociates a payment method with a specific restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Disassociation successful"),
            @ApiResponse(code = 404, message = "Restaurant or payment method not found", response = Error.class)
    })
    ResponseEntity<Void> remove(@ApiParam(value = "Restaurant identifier", example = "1") Long restaurantId,
                                @ApiParam(value = "Payment method identifier", example = "1") Long paymentMethodId);
}
