package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Address;
import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestaurantMixin {

    @JsonIgnoreProperties(value = "type", allowGetters = true)
    private Category category;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private LocalDateTime created;

    @JsonIgnore
    private LocalDateTime updated;

    @JsonIgnore
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
