package com.algaworks.algafood.domain.event.listener;

import com.algaworks.algafood.domain.event.OrderConfirmedEvent;
import com.algaworks.algafood.domain.service.OrderEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmedListener {

    private final OrderEmailService orderEmailService;

    @Autowired
    public OrderConfirmedListener(OrderEmailService orderEmailService) {
        this.orderEmailService = orderEmailService;
    }

    @EventListener
    public void processOrderConfirmation(OrderConfirmedEvent event) {
        orderEmailService.sendConfirmationEmail(event.getOrder());
    }
}
