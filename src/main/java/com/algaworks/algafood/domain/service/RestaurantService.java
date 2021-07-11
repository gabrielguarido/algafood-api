package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CategoryNotFoundException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.exception.ValidationException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

@Service
public class RestaurantService {

    private static final String RESTAURANT_IN_USE_EXCEPTION_MESSAGE = "The restaurant ID %s is currently being used and cannot be removed";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SmartValidator smartValidator;

    public List<Restaurant> list() {
        return restaurantRepository.findAll();
    }

    public Restaurant find(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    public List<Restaurant> listByDeliveryFee(BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        return restaurantRepository.queryByDeliveryFeeBetween(initialDeliveryFee, finalDeliveryFee);
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

    public List<Restaurant> customSearch(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        return restaurantRepository.find(name, initialDeliveryFee, finalDeliveryFee);
    }

    public Integer countByCategory(Long categoryId) {
        return restaurantRepository.countByCategoryId(categoryId);
    }

    public List<Restaurant> findWithFreeDelivery(String name) {
        return restaurantRepository.findWithFreeDeliveryFee(name);
    }

    public Restaurant findFirst() {
        return restaurantRepository.findFirst().orElse(null);
    }

    public Restaurant save(Restaurant restaurant) {
        try {
            categoryService.find(restaurant.getCategory().getId());
        } catch (CategoryNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return restaurantRepository.save(restaurant);
    }

    public Restaurant update(Long id, Restaurant restaurant) {
        try {
            var existingRestaurant = find(id);

            BeanUtils.copyProperties(restaurant, existingRestaurant, "id", "paymentMethods", "address", "created", "products");

            return save(existingRestaurant);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Restaurant updatePartially(Long id, Map<String, Object> fields, HttpServletRequest request) {
        try {
            var existingRestaurant = find(id);

            merge(fields, existingRestaurant, request);

            validate(existingRestaurant, "restaurant");

            return update(id, existingRestaurant);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(RESTAURANT_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(id);
        }
    }

    private void merge(Map<String, Object> fields, Restaurant restaurant, HttpServletRequest request) {
        var httpRequest = new ServletServerHttpRequest(request);

        try {
            var objectMapper = new ObjectMapper();

            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

            var convertedRestaurant = objectMapper.convertValue(fields, Restaurant.class);

            fields.forEach((key, value) -> {
                var field = ReflectionUtils.findField(Restaurant.class, key);
                assert field != null;
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, convertedRestaurant);

                ReflectionUtils.setField(field, restaurant, newValue);
            });
        } catch (IllegalArgumentException e) {
            throw new HttpMessageNotReadableException(e.getMessage(), getRootCause(e), httpRequest);
        }
    }

    private void validate(Restaurant restaurant, String objectName) {
        var bindingResult = new BeanPropertyBindingResult(restaurant, objectName);

        smartValidator.validate(restaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }
}
