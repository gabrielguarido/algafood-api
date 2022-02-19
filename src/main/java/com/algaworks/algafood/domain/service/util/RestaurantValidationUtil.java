package com.algaworks.algafood.domain.service.util;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CategoryNotFoundException;
import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.CategoryService;
import com.algaworks.algafood.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantValidationUtil {

    private final CategoryService categoryService;

    private final CityService cityService;

    @Autowired
    public RestaurantValidationUtil(CategoryService categoryService, CityService cityService) {
        this.categoryService = categoryService;
        this.cityService = cityService;
    }

    public void validateCategory(Long categoryId, Restaurant restaurant) {
        try {
            var category = categoryService.verifyIfExists(categoryId);
            restaurant.setCategory(category);
        } catch (CategoryNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public void validateCity(Long cityId, Restaurant restaurant) {
        try {
            var city = cityService.verifyIfExists(cityId);
            restaurant.getAddress().setCity(city);
        } catch (CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
