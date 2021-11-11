package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderTransformer {

    private final ModelMapper mapper;

    @Autowired
    public OrderTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public OrderResponse toResponse(Order order) {
        return mapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> toResponse(List<Order> orderList) {
        return orderList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public OrderModelResponse toModelResponse(Order order) {
        return mapper.map(order, OrderModelResponse.class);
    }

    public List<OrderModelResponse> toModelResponse(List<Order> orderList) {
        return orderList.stream()
                .map(this::toModelResponse)
                .collect(toList());
    }
}
