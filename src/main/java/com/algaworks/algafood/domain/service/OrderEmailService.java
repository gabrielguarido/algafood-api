package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderEmailService {

    private final EmailSenderService emailSenderService;

    @Autowired
    public OrderEmailService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public void sendConfirmationEmail(Order order) {
        var message = EmailSenderService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order confirmed")
                .body("order-confirmed.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .build();

        emailSenderService.send(message);
    }
}
