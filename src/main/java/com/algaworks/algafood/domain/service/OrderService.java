package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.OrderRequest;
import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.api.transformer.OrderTransformer;
import com.algaworks.algafood.domain.exception.OrderNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.model.enumerator.OrderStatus;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;
import com.algaworks.algafood.domain.service.util.OrderValidationUtil;
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

    private final OrderRepository orderRepository;

    private final OrderTransformer orderTransformer;

    private final OrderValidationUtil orderValidationUtil;

    private final OrderEmailService orderEmailService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderTransformer orderTransformer,
                        OrderValidationUtil orderValidationUtil, OrderEmailService orderEmailService) {
        this.orderRepository = orderRepository;
        this.orderTransformer = orderTransformer;
        this.orderValidationUtil = orderValidationUtil;
        this.orderEmailService = orderEmailService;
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
        orderValidationUtil.validateOrder(order);
        orderValidationUtil.validateOrderItems(order);
        calculateTotalValue(order);

        return orderTransformer.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void updateOrderStatus(UUID externalKey, OrderStatus targetStatus) {
        var order = verifyIfExists(externalKey);

        switch (targetStatus) {
            case CONFIRMED:
                order.confirm();
                orderEmailService.sendConfirmationEmail(order);
                break;

            case DELIVERED:
                order.deliver();
                break;

            case CANCELED:
                order.cancel();
                break;
        }
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
