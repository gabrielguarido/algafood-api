package com.algaworks.algafood.domain.event.listener;

import com.algaworks.algafood.domain.event.OrderCancelledEvent;
import com.algaworks.algafood.domain.service.OrderEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCancelledListener {

    private final OrderEmailService orderEmailService;

    @Autowired
    public OrderCancelledListener(OrderEmailService orderEmailService) {
        this.orderEmailService = orderEmailService;
    }

    @TransactionalEventListener
    public void processOrderCancellation(OrderCancelledEvent event) {
        orderEmailService.sendCancellationEmail(event.getOrder());
    }
}
