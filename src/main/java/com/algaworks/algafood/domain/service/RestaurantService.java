package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.RestaurantRequest;
import com.algaworks.algafood.api.model.RestaurantResponse;
import com.algaworks.algafood.api.transformer.RestaurantTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CategoryNotFoundException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private static final String RESTAURANT_IN_USE_EXCEPTION_MESSAGE = "The restaurant ID %s is currently being used and cannot be removed";

    private final RestaurantRepository restaurantRepository;

    private final CategoryService categoryService;

    private final RestaurantTransformer restaurantTransformer;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, CategoryService categoryService, RestaurantTransformer restaurantTransformer) {
        this.restaurantRepository = restaurantRepository;
        this.categoryService = categoryService;
        this.restaurantTransformer = restaurantTransformer;
    }

    public List<RestaurantResponse> list() {
        return restaurantTransformer.toResponse(restaurantRepository.findAll());
    }

    public RestaurantResponse find(Long id) {
        return restaurantTransformer.toResponse(verifyIfExists(id));
    }

    public List<RestaurantResponse> listByDeliveryFee(BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        return restaurantTransformer.toResponse(restaurantRepository.queryByDeliveryFeeBetween(initialDeliveryFee, finalDeliveryFee));
    }

    public List<RestaurantResponse> listByName(String name) {
        return restaurantTransformer.toResponse(restaurantRepository.findByName(name));
    }

    public RestaurantResponse findFirstByName(String name) {
        Optional<Restaurant> restaurant = restaurantRepository.findFirstByNameContaining(name);

        if (restaurant.isEmpty()) {
            return null;
        }

        return restaurantTransformer.toResponse(restaurant.get());
    }

    public List<RestaurantResponse> findTop2ByName(String name) {
        return restaurantTransformer.toResponse(restaurantRepository.findTop2ByNameContaining(name));
    }

    public List<RestaurantResponse> customSearch(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        return restaurantTransformer.toResponse(restaurantRepository.find(name, initialDeliveryFee, finalDeliveryFee));
    }

    public Integer countByCategory(Long categoryId) {
        return restaurantRepository.countByCategoryId(categoryId);
    }

    public List<RestaurantResponse> findWithFreeDelivery(String name) {
        return restaurantTransformer.toResponse(restaurantRepository.findWithFreeDeliveryFee(name));
    }

    public RestaurantResponse findFirst() {
        return restaurantTransformer.toResponse(restaurantRepository.findFirst().orElse(null));
    }

    @Transactional
    public RestaurantResponse save(RestaurantRequest restaurantRequest) {
        var restaurant = restaurantTransformer.toEntity(restaurantRequest);

        validateCategory(restaurant.getCategory().getId());

        return restaurantTransformer.toResponse(restaurantRepository.save(restaurant));
    }

    @Transactional
    public RestaurantResponse update(Long id, RestaurantRequest restaurantRequest) {
        try {
            var existingRestaurant = verifyIfExists(id);

            restaurantTransformer.copyPropertiesToEntity(restaurantRequest, existingRestaurant);

            validateCategory(existingRestaurant.getCategory().getId());

            return restaurantTransformer.toResponse(restaurantRepository.save(existingRestaurant));
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(RESTAURANT_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(id);
        }
    }

    private Restaurant verifyIfExists(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    private void validateCategory(Long categoryId) {
        try {
            categoryService.find(categoryId);
        } catch (CategoryNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
