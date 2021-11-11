package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.api.transformer.OrderTransformer;
import com.algaworks.algafood.domain.exception.OrderNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderTransformer orderTransformer;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderTransformer orderTransformer) {
        this.orderRepository = orderRepository;
        this.orderTransformer = orderTransformer;
    }

    public List<OrderModelResponse> list() {
        return orderTransformer.toModelResponse(orderRepository.findAll());
    }

    public OrderResponse find(Long id) {
        return orderTransformer.toResponse(verifyIfExists(id));
    }

    private Order verifyIfExists(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
