package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CategoryMixin {

    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();
}
