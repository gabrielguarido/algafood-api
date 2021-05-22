package com.algaworks.algafood.domain.repository.query;

import com.algaworks.algafood.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {

    List<Restaurant> find(String name, BigDecimal initialShippingTax, BigDecimal finalShippingTax);

    List<Restaurant> findWithFreeShippingTax(String name);
}
