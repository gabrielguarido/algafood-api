package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.controller.documentation.RestaurantControllerDocumentation;
import com.algaworks.algafood.api.model.request.RestaurantRequest;
import com.algaworks.algafood.api.model.response.RestaurantResponse;
import com.algaworks.algafood.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.algaworks.algafood.api.controller.util.ResourceUriUtil.composeUri;

@RestController
@RequestMapping(value = "restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerDocumentation {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> list() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.find(id));
    }

    @GetMapping("/by-delivery-fee")
    public ResponseEntity<List<RestaurantResponse>> listByDeliveryFee(BigDecimal initialFee, BigDecimal finalFee) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.listByDeliveryFee(initialFee, finalFee));
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<RestaurantResponse>> listByName(String name) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.listByName(name));
    }

    @GetMapping("/first-by-name")
    public ResponseEntity<RestaurantResponse> findFirstByName(String name) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.findFirstByName(name));
    }

    @GetMapping("/top2-by-name")
    public ResponseEntity<List<RestaurantResponse>> findTop2ByName(String name) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.findTop2ByName(name));
    }

    @GetMapping("/custom")
    public ResponseEntity<List<RestaurantResponse>> customSearch(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        return ResponseEntity.ok(restaurantService.customSearch(name, initialDeliveryFee, finalDeliveryFee));
    }

    @GetMapping("/count-by-category")
    public ResponseEntity<Integer> countByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(restaurantService.countByCategory(categoryId));
    }

    @GetMapping("/free-delivery")
    public ResponseEntity<List<RestaurantResponse>> findWithFreeDelivery(String name) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.findWithFreeDelivery(name));
    }

    @GetMapping("/first")
    public ResponseEntity<RestaurantResponse> findFirst() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(restaurantService.findFirst());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantResponse> create(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        var newRestaurant = restaurantService.save(restaurantRequest);

        return ResponseEntity.created(composeUri(newRestaurant.getId())).body(newRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable Long id, @RequestBody @Valid RestaurantRequest restaurantRequest) {
        return ResponseEntity.ok(restaurantService.update(id, restaurantRequest));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        restaurantService.activate(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        restaurantService.deactivate(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/open")
    public ResponseEntity<Void> open(@PathVariable Long id) {
        restaurantService.open(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Void> close(@PathVariable Long id) {
        restaurantService.close(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
