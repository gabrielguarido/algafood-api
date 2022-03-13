package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.OrderControllerDocumentation;
import com.algaworks.algafood.api.model.request.OrderRequest;
import com.algaworks.algafood.api.model.response.OrderModelResponse;
import com.algaworks.algafood.api.model.response.OrderResponse;
import com.algaworks.algafood.domain.repository.filter.OrderFilter;
import com.algaworks.algafood.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.CANCELED;
import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.CONFIRMED;
import static com.algaworks.algafood.domain.model.enumerator.OrderStatus.DELIVERED;

@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerDocumentation {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderModelResponse>> search(OrderFilter filter, Pageable pageable) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(orderService.list(filter, pageable));
    }

    @GetMapping("/{externalKey}")
    public ResponseEntity<OrderResponse> find(@PathVariable UUID externalKey) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(orderService.find(externalKey));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> issueOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.issueOrder(orderRequest));
    }

    @PutMapping("/{externalKey}/confirm")
    public ResponseEntity<Void> confirmOrder(@PathVariable UUID externalKey) {
        orderService.updateOrderStatus(externalKey, CONFIRMED);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{externalKey}/deliver")
    public ResponseEntity<Void> deliverOrder(@PathVariable UUID externalKey) {
        orderService.updateOrderStatus(externalKey, DELIVERED);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{externalKey}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID externalKey) {
        orderService.updateOrderStatus(externalKey, CANCELED);

        return ResponseEntity.noContent().build();
    }
}
