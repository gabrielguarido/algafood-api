package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.OrderRequest;
import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.api.transformer.OrderTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.OrderNotFoundException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.model.enumerator.OrderStatus;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;
import com.algaworks.algafood.infrastructure.repository.spec.OrderSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private static final String PAYMENT_METHOD_NOT_ACCEPTED_EXCEPTION_MESSAGE = "The restaurant does not accept the payment method '%s'";

    private final OrderRepository orderRepository;

    private final OrderTransformer orderTransformer;

    private final RestaurantService restaurantService;

    private final CityService cityService;

    private final UserService userService;

    private final ProductService productService;

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderTransformer orderTransformer, CityService cityService,
                        RestaurantService restaurantService, PaymentMethodService paymentMethodService,
                        ProductService productService, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderTransformer = orderTransformer;
        this.restaurantService = restaurantService;
        this.cityService = cityService;
        this.productService = productService;
        this.paymentMethodService = paymentMethodService;
        this.userService = userService;
    }

    public Page<OrderModelResponse> list(OrderFilter filter, Pageable pageable) {
        Page<Order> page = orderRepository.findAll(OrderSpecs.withFilter(filter), pageable);

        List<OrderModelResponse> response = orderTransformer.toModelResponse(page.getContent());

        return new PageImpl<>(response, pageable, page.getTotalElements());
    }

    public OrderResponse find(UUID externalKey) {
        return orderTransformer.toResponse(verifyIfExists(externalKey));
    }

    @Transactional
    public OrderResponse issueOrder(OrderRequest orderRequest) {
        var order = orderTransformer.toEntity(orderRequest);

        getLoggedUser(order);
        validateOrder(order);
        validateOrderItems(order);
        calculateTotalValue(order);

        return orderTransformer.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void updateOrderStatus(UUID externalKey, OrderStatus targetStatus) {
        var order = verifyIfExists(externalKey);

        switch (targetStatus) {
            case CONFIRMED:
                order.confirm();
                break;

            case DELIVERED:
                order.deliver();
                break;

            case CANCELED:
                order.cancel();
                break;
        }
    }

    private void validateOrder(Order order) {
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

    private void validateOrderItems(Order order) {
        order.getItems().forEach(orderItem -> {
            var product = productService.verifyIfExists(order.getRestaurant().getId(), orderItem.getProduct().getId());

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
        });
    }

    private void calculateTotalValue(Order order) {
        order.setDeliveryFee(order.getRestaurant().getDeliveryFee());
        order.calculateTotalValue();
    }

    private void getLoggedUser(Order order) {
        // TODO: Get authenticated user
        order.setClient(new User());
        order.getClient().setId(1L);
    }

    private Order verifyIfExists(UUID externalKey) {
        return orderRepository.findByExternalKey(externalKey.toString()).orElseThrow(() -> new OrderNotFoundException(externalKey));
    }
}
