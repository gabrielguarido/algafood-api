package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "restaurant/{restaurantId}/responsible-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResponsibleUserController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantResponsibleUserController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> list(@PathVariable Long restaurantId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.listResponsibleUsers(restaurantId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> add(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantService.addResponsibleUser(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> remove(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantService.removeResponsibleUser(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
