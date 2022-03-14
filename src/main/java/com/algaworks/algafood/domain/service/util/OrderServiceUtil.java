package com.algaworks.algafood.domain.service.util;

import com.algaworks.algafood.core.security.util.SecurityUtil;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceUtil {

    private final SecurityUtil securityUtil;

    @Autowired
    public OrderServiceUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    public void calculateTotalValue(Order order) {
        order.setDeliveryFee(order.getRestaurant().getDeliveryFee());
        order.calculateTotalValue();
    }

    public void getLoggedUser(Order order) {
        var loggedUser = new User();
        loggedUser.setId(securityUtil.getLoggedUserId());

        order.setClient(loggedUser);
    }
}
