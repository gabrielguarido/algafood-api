package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.algaworks.algafood.infrastructure.groups.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> list() {
        return ResponseEntity.ok(restaurantService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> find(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.find(id));
    }

    @GetMapping("/by-shipping-tax")
    public ResponseEntity<List<Restaurant>> listByShippingTax(BigDecimal initialShippingTax, BigDecimal finalShippingTax) {
        return ResponseEntity.ok(restaurantService.listByShippingTax(initialShippingTax, finalShippingTax));
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<Restaurant>> listByName(String name) {
        return ResponseEntity.ok(restaurantService.listByName(name));
    }

    @GetMapping("/first-by-name")
    public ResponseEntity<Restaurant> findFirstByName(String name) {
        return ResponseEntity.ok(restaurantService.findFirstByName(name));
    }

    @GetMapping("/top2-by-name")
    public ResponseEntity<List<Restaurant>> findTop2ByName(String name) {
        return ResponseEntity.ok(restaurantService.findTop2ByName(name));
    }

    @GetMapping("/custom")
    public ResponseEntity<List<Restaurant>> customSearch(String name, BigDecimal initialShippingTax, BigDecimal finalShippingTax) {
        return ResponseEntity.ok(restaurantService.customSearch(name, initialShippingTax, finalShippingTax));
    }

    @GetMapping("/count-by-category")
    public ResponseEntity<Integer> countByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(restaurantService.countByCategory(categoryId));
    }

    @GetMapping("/free-shipping")
    public ResponseEntity<List<Restaurant>> findWithFreeShipping(String name) {
        return ResponseEntity.ok(restaurantService.findWithFreeShipping(name));
    }

    @GetMapping("/first")
    public ResponseEntity<Restaurant> findFirst() {
        return ResponseEntity.ok(restaurantService.findFirst());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@RequestBody @Valid Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.save(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> update(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.update(id, restaurant));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartially(@PathVariable Long id, @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(restaurantService.updatePartially(id, fields, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id) {
        restaurantService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
