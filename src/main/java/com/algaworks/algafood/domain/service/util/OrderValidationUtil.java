package com.algaworks.algafood.domain.service.util;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.CityService;
import com.algaworks.algafood.domain.service.PaymentMethodService;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.algaworks.algafood.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderValidationUtil {

    private static final String PAYMENT_METHOD_NOT_ACCEPTED_EXCEPTION_MESSAGE = "The restaurant does not accept the payment method '%s'";

    private final CityService cityService;

    private final UserService userService;

    private final RestaurantService restaurantService;

    private final PaymentMethodService paymentMethodService;

    private final ProductService productService;

    @Autowired
    public OrderValidationUtil(CityService cityService, UserService userService, RestaurantService restaurantService,
                               PaymentMethodService paymentMethodService, ProductService productService) {
        this.cityService = cityService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.paymentMethodService = paymentMethodService;
        this.productService = productService;
    }

    public void validateOrder(Order order) {
        try {
            var city = cityService.verifyIfExists(order.getDeliveryAddress().getCity().getId());
            var client = userService.verifyIfExists(order.getClient().getId());
            var restaurant = restaurantService.verifyIfExists(order.getRestaurant().getId());
            var paymentMethod = paymentMethodService.verifyIfExists(order.getPaymentMethod().getId());

            order.getDeliveryAddress().setCity(city);
            order.setClient(client);
            order.setRestaurant(restaurant);
            order.setPaymentMethod(paymentMethod);

            if (!restaurant.acceptsPaymentMethod(paymentMethod)) {
                throw new BusinessException(
                        String.format(PAYMENT_METHOD_NOT_ACCEPTED_EXCEPTION_MESSAGE, paymentMethod.getMethod())
                );
            }
        } catch (ResourceNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void validateOrderItems(Order order) {
        order.getItems().forEach(orderItem -> {
            var product = productService.verifyIfExists(order.getRestaurant().getId(), orderItem.getProduct().getId());

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
        });
    }
}
