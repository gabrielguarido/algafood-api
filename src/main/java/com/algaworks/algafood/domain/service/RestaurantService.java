package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Restaurant> list() {
        return restaurantRepository.findAll();
    }

    public Restaurant find(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Restaurant ID %s not found", id)
        ));
    }

    public Restaurant save(Restaurant restaurant) {
        categoryService.find(restaurant.getCategory().getId());

        return restaurantRepository.save(restaurant);
    }

    public Restaurant update(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = find(id);

        BeanUtils.copyProperties(restaurant, existingRestaurant, "id");

        return save(existingRestaurant);
    }
}
