package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.repository.CategoryRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static com.algaworks.algafood.util.ApiTestUtil.addTestData;
import static com.algaworks.algafood.util.ApiTestUtil.buildRequestSpecification;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerIT {

    @LocalServerPort
    private int applicationPort;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    CategoryRepository categoryRepository;

    private static final String BASE_PATH = "/category";

    @BeforeAll
    void setUpTestContext() {
        enableLoggingOfRequestAndResponseIfValidationFails();

        databaseCleaner.clearTables();
        addTestData(categoryRepository);
    }

    @Test
    void shouldReturnHttpStatus200_WhenSearchForCategories() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    void shouldContainTypes_WhenSearchForCategories() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .when()
                .get()
                .then()
                .body("type", hasItems("Australian", "Burger"));
    }

    @Test
    void shouldReturnHttpStatus200_WhenSearchForExistingCategoryId() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .pathParam("categoryId", 1)
                .when()
                .get("/{categoryId}")
                .then()
                .statusCode(OK.value())
                .body("type", equalTo("Burger"));
    }

    @Test
    void shouldReturnHttpStatus404_WhenSearchForNonexistentCategoryId() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .pathParam("categoryId", 999)
                .when()
                .get("/{categoryId}")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldReturnHttpStatus201_WhenCreateNewCategory() {
        String requestPayload = ResourceUtil.getContentFromResource("/json/success/create_category.json");

        buildRequestSpecification(applicationPort, BASE_PATH)
                .body(requestPayload)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value());
    }
}
