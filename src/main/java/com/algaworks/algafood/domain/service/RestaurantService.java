package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.math.BigDecimal;
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

    public List<Restaurant> listByShippingTax(BigDecimal initialShippingTax, BigDecimal finalShippingTax) {
        return restaurantRepository.queryByShippingTaxBetween(initialShippingTax, finalShippingTax);
    }

    public List<Restaurant> listByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public Restaurant findFirstByName(String name) {
        return restaurantRepository.findFirstByNameContaining(name).orElse(null);
    }

    public List<Restaurant> findTop2ByName(String name) {
        return restaurantRepository.findTop2ByNameContaining(name);
    }

    public List<Restaurant> customSearch(String name, BigDecimal initialShippingTax, BigDecimal finalShippingTax) {
        return restaurantRepository.find(name, initialShippingTax, finalShippingTax);
    }

    public Integer countByCategory(Long categoryId) {
        return restaurantRepository.countByCategoryId(categoryId);
    }

    public List<Restaurant> findWithFreeShipping(String name) {
        return restaurantRepository.findWithFreeShippingTax(name);
    }

    public Restaurant findFirst() {
        return restaurantRepository.findFirst().orElse(null);
    }

    public Restaurant save(Restaurant restaurant) {
        categoryService.find(restaurant.getCategory().getId());

        return restaurantRepository.save(restaurant);
    }

    public Restaurant update(Long id, Restaurant restaurant) {
        var existingRestaurant = find(id);

        BeanUtils.copyProperties(restaurant, existingRestaurant, "id", "paymentMethods");

        return save(existingRestaurant);
    }

    public Restaurant updatePartially(Long id, Map<String, Object> fields) {
        var existingRestaurant = find(id);

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
        var objectMapper = new ObjectMapper();
        var convertedRestaurant = objectMapper.convertValue(fields, Restaurant.class);

        fields.forEach((key, value) -> {
            var field = ReflectionUtils.findField(Restaurant.class, key);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, convertedRestaurant);

            ReflectionUtils.setField(field, restaurant, newValue);
        });
    }
}
