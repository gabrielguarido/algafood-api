package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.RestaurantRequest;
import com.algaworks.algafood.api.model.response.PaymentMethodResponse;
import com.algaworks.algafood.api.model.response.RestaurantResponse;
import com.algaworks.algafood.api.model.response.UserResponse;
import com.algaworks.algafood.api.transformer.PaymentMethodTransformer;
import com.algaworks.algafood.api.transformer.RestaurantTransformer;
import com.algaworks.algafood.api.transformer.UserTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CategoryNotFoundException;
import com.algaworks.algafood.domain.exception.CityNotFoundException;
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
    private static final String RESTAURANT_ALREADY_ACTIVE_EXCEPTION_MESSAGE = "The restaurant ID %s is already active";
    private static final String RESTAURANT_ALREADY_INACTIVE_EXCEPTION_MESSAGE = "The restaurant ID %s is already inactive";

    private final RestaurantRepository restaurantRepository;

    private final CategoryService categoryService;

    private final CityService cityService;

    private final PaymentMethodService paymentMethodService;

    private final RestaurantTransformer restaurantTransformer;

    private final PaymentMethodTransformer paymentMethodTransformer;

    private final UserService userService;

    private final UserTransformer userTransformer;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, CategoryService categoryService,
                             CityService cityService, PaymentMethodTransformer paymentMethodTransformer,
                             PaymentMethodService paymentMethodService, RestaurantTransformer restaurantTransformer,
                             UserService userService, UserTransformer userTransformer) {
        this.restaurantRepository = restaurantRepository;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.paymentMethodTransformer = paymentMethodTransformer;
        this.paymentMethodService = paymentMethodService;
        this.restaurantTransformer = restaurantTransformer;
        this.userService = userService;
        this.userTransformer = userTransformer;
    }

    public List<RestaurantResponse> list() {
        return restaurantTransformer.toResponse(restaurantRepository.findAll());
    }

    public RestaurantResponse find(Long id) {
        return restaurantTransformer.toResponse(verifyIfExists(id));
    }

    public List<PaymentMethodResponse> listPaymentMethods(Long restaurantId) {
        var restaurant = verifyIfExists(restaurantId);

        return paymentMethodTransformer.toResponse(restaurant.getPaymentMethods());
    }

    public List<RestaurantResponse> listByDeliveryFee(BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {
        return restaurantTransformer.toResponse(
                restaurantRepository.queryByDeliveryFeeBetween(initialDeliveryFee, finalDeliveryFee)
        );
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

        validateCategory(restaurant.getCategory().getId(), restaurant);
        validateCity(restaurant.getAddress().getCity().getId(), restaurant);

        return restaurantTransformer.toResponse(restaurantRepository.save(restaurant));
    }

    @Transactional
    public RestaurantResponse update(Long id, RestaurantRequest restaurantRequest) {
        try {
            var existingRestaurant = verifyIfExists(id);

            restaurantTransformer.copyPropertiesToEntity(restaurantRequest, existingRestaurant);

            validateCategory(existingRestaurant.getCategory().getId(), existingRestaurant);
            validateCity(existingRestaurant.getAddress().getCity().getId(), existingRestaurant);

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

    @Transactional
    public void activate(Long id) {
        var restaurant = verifyIfExists(id);

        if (Boolean.TRUE.equals(restaurant.getActive())) {
            throw new BusinessException(String.format(RESTAURANT_ALREADY_ACTIVE_EXCEPTION_MESSAGE, id));
        }

        restaurant.activate();
    }

    @Transactional
    public void deactivate(Long id) {
        var restaurant = verifyIfExists(id);

        if (Boolean.FALSE.equals(restaurant.getActive())) {
            throw new BusinessException(String.format(RESTAURANT_ALREADY_INACTIVE_EXCEPTION_MESSAGE, id));
        }

        restaurant.deactivate();
    }

    @Transactional
    public void open(Long id) {
        var restaurant = verifyIfExists(id);

        restaurant.open();
    }

    @Transactional
    public void close(Long id) {
        var restaurant = verifyIfExists(id);

        restaurant.close();
    }

    @Transactional
    public void addPaymentMethod(Long restaurantId, Long paymentMethodId) {
        var restaurant = verifyIfExists(restaurantId);
        var paymentMethod = paymentMethodService.verifyIfExists(paymentMethodId);

        restaurant.addPaymentMethod(paymentMethod);
    }

    @Transactional
    public void removePaymentMethod(Long restaurantId, Long paymentMethodId) {
        var restaurant = verifyIfExists(restaurantId);
        var paymentMethod = paymentMethodService.verifyIfExists(paymentMethodId);

        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public void addResponsibleUser(Long restaurantId, Long userId) {
        var restaurant = verifyIfExists(restaurantId);
        var responsibleUser = userService.verifyIfExists(userId);

        restaurant.addResponsibleUser(responsibleUser);
    }

    @Transactional
    public void removeResponsibleUser(Long restaurantId, Long userId) {
        var restaurant = verifyIfExists(restaurantId);
        var responsibleUser = userService.verifyIfExists(userId);

        restaurant.removeResponsibleUser(responsibleUser);
    }

    public List<UserResponse> listResponsibleUsers(Long restaurantId) {
        var restaurant = verifyIfExists(restaurantId);

        return userTransformer.toResponse(restaurant.getResponsibleUsers());
    }

    public Restaurant verifyIfExists(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    private void validateCategory(Long categoryId, Restaurant restaurant) {
        try {
            var category = categoryService.verifyIfExists(categoryId);
            restaurant.setCategory(category);
        } catch (CategoryNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private void validateCity(Long cityId, Restaurant restaurant) {
        try {
            var city = cityService.verifyIfExists(cityId);
            restaurant.getAddress().setCity(city);
        } catch (CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
