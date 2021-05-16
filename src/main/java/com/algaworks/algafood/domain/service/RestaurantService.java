package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    public Restaurant updatePartially(Long id, Map<String, Object> fields) {
        Restaurant existingRestaurant = find(id);

        merge(fields, existingRestaurant);

        return update(id, existingRestaurant);
    }

    public void delete(Long id) {
        try {
            restaurantRepository.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format("The restaurant %s is currently being used and cannot be removed", id)
            );
        }
    }

    private void merge(Map<String, Object> fields, Restaurant restaurant) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant convertedRestaurant = objectMapper.convertValue(fields, Restaurant.class);

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, key);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, convertedRestaurant);

            ReflectionUtils.setField(field, restaurant, newValue);
        });
    }
}
