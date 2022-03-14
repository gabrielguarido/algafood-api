package com.algaworks.algafood.api.controller.security;

import com.algaworks.algafood.api.model.request.OrderRequest;
import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.core.security.annotation.HasAuthority;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Api(tags = "Orders")
public interface OrderControllerSecurity {

    @HasAuthority.Order.Query
    ResponseEntity<Page<OrderModelResponse>> search(OrderFilter filter, Pageable pageable);

    @HasAuthority.Order.Find
    ResponseEntity<OrderResponse> find(UUID externalKey);

    @HasAuthority.Order.Manage
    ResponseEntity<OrderResponse> issueOrder(OrderRequest orderRequest);

    @HasAuthority.Order.Manage
    ResponseEntity<Void> confirmOrder(UUID externalKey);

    @HasAuthority.Order.Manage
    ResponseEntity<Void> deliverOrder(UUID externalKey);

    @HasAuthority.Order.Manage
    ResponseEntity<Void> cancelOrder(UUID externalKey);
}
