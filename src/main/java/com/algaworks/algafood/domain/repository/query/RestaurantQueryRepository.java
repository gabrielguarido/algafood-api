package com.algaworks.algafood.domain.repository.query;

import com.algaworks.algafood.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantQueryRepository {

    List<Restaurant> find(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee);

    List<Restaurant> findWithFreeDeliveryFee(String name);
}
