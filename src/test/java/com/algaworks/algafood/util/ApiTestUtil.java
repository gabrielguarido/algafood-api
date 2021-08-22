package com.algaworks.algafood.util;

import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.CategoryRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import io.restassured.specification.RequestSpecification;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public abstract class ApiTestUtil {

    public static RequestSpecification buildRequestSpecification(int applicationPort, String basePath) {
        return given()
                .basePath(basePath)
                .port(applicationPort)
                .accept(JSON)
                .contentType(JSON);
    }

    public static void addTestData(CategoryRepository categoryRepository) {
        Category category1 = Category.builder()
                .type("Burger")
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .type("Australian")
                .build();
        categoryRepository.save(category2);
    }

    public static void addTestData(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository) {
        Category category = Category.builder()
                .type("Brazilian")
                .build();
        categoryRepository.save(category);

        Restaurant restaurant1 = Restaurant.builder()
                .name("Fogo de Chão")
                .deliveryFee(BigDecimal.valueOf(15.00))
                .category(category)
                .build();
        restaurantRepository.save(restaurant1);

        Restaurant restaurant2 = Restaurant.builder()
                .name("Montanna Grill")
                .deliveryFee(BigDecimal.valueOf(5.00))
                .category(category)
                .build();
        restaurantRepository.save(restaurant2);
    }
}
