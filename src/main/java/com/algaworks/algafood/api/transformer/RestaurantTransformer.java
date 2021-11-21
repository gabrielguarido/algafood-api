package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.RestaurantRequest;
import com.algaworks.algafood.api.model.response.RestaurantResponse;
import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RestaurantTransformer {

    private final ModelMapper mapper;

    @Autowired
    public RestaurantTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public RestaurantResponse toResponse(Restaurant restaurant) {
        return mapper.map(restaurant, RestaurantResponse.class);
    }

    public List<RestaurantResponse> toResponse(List<Restaurant> restaurantList) {
        return restaurantList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public Restaurant toEntity(RestaurantRequest restaurantRequest) {
        return mapper.map(restaurantRequest, Restaurant.class);
    }

    public void copyPropertiesToEntity(RestaurantRequest restaurantRequest, Restaurant restaurant) {
        restaurant.setCategory(new Category());

        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }

        mapper.map(restaurantRequest, restaurant);
    }
}
