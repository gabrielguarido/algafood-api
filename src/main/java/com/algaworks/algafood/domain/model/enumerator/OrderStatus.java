package com.algaworks.algafood.domain.model.enumerator;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    CREATED,
    CONFIRMED(CREATED),
    DELIVERED(CONFIRMED),
    CANCELED(CREATED, CONFIRMED);

    private final List<OrderStatus> previousStatus;

    OrderStatus(OrderStatus... previousStatus) {
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public boolean canUpdateTo(OrderStatus targetStatus) {
        return targetStatus.previousStatus.contains(this);
    }
}
