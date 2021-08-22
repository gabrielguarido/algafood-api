package com.algaworks.algafood.api.resource;

import com.algaworks.algafood.domain.repository.CategoryRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantResourceIT {

    @LocalServerPort
    private int applicationPort;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String BASE_PATH = "/restaurant";

    @BeforeAll
    void setUpTestContext() {
        enableLoggingOfRequestAndResponseIfValidationFails();

//        databaseCleaner.clearTables();
        addTestData(restaurantRepository, categoryRepository);
    }

    @Test
    void shouldReturnHttpStatus200_WhenSearchForRestaurants() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    void shouldReturnHttpStatus201_WhenCreateNewRestaurant() {
        String requestPayload = ResourceUtils.getContentFromResource("/json/success/create_restaurant.json");

        buildRequestSpecification(applicationPort, BASE_PATH)
                .body(requestPayload)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value());
    }

    @Test
    void shouldReturnHttpStatus400_WhenCreateNewRestaurantWithoutCategory() {
        String requestPayload = ResourceUtils.getContentFromResource("/json/success/create_restaurant_without_category.json");

        buildRequestSpecification(applicationPort, BASE_PATH)
                .body(requestPayload)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldReturnHttpStatus400_WhenCreateNewRestaurantWithInvalidCategory() {
        String requestPayload = ResourceUtils.getContentFromResource("/json/success/create_restaurant_with_invalid_category.json");

        buildRequestSpecification(applicationPort, BASE_PATH)
                .body(requestPayload)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldReturnHttpStatus400_WhenCreateNewRestaurantWithoutDeliveryFee() {
        String requestPayload = ResourceUtils.getContentFromResource("/json/success/create_restaurant_without_delivery_fee.json");

        buildRequestSpecification(applicationPort, BASE_PATH)
                .body(requestPayload)
                .when()
                .post()
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldReturnHttpStatus200_WhenSearchForExistingRestaurantId() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .pathParam("restaurantId", 1)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(OK.value())
                .body("name", equalTo("Fogo de Chão"));
    }

    @Test
    void shouldReturnHttpStatus404_WhenSearchForNonexistentRestaurantId() {
        buildRequestSpecification(applicationPort, BASE_PATH)
                .pathParam("restaurantId", 999)
                .when()
                .get("/{restaurantId}")
                .then()
                .statusCode(NOT_FOUND.value());
    }
}
