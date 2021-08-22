package com.algaworks.algafood.util;

import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.repository.CategoryRepository;
import io.restassured.specification.RequestSpecification;

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
}
