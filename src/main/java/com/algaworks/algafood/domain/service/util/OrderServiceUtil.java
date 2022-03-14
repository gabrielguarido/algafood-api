package com.algaworks.algafood.domain.service.util;

import com.algaworks.algafood.core.security.util.SecurityUtil;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceUtil {

    public void calculateTotalValue(Order order) {
        order.setDeliveryFee(order.getRestaurant().getDeliveryFee());
        order.calculateTotalValue();
    }

    public void getLoggedUser(Order order) {
        var loggedUser = new User();
        loggedUser.setId(SecurityUtil.getLoggedUserId());

        order.setClient(loggedUser);
    }
}
