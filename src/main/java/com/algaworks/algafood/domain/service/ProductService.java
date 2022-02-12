package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.api.transformer.ProductPictureTransformer;
import com.algaworks.algafood.api.transformer.ProductTransformer;
import com.algaworks.algafood.domain.exception.ProductNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductTransformer productTransformer;

    private final ProductPictureTransformer productPictureTransformer;

    private final RestaurantService restaurantService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductTransformer productTransformer,
                          RestaurantService restaurantService, ProductPictureTransformer productPictureTransformer) {
        this.productRepository = productRepository;
        this.productTransformer = productTransformer;
        this.productPictureTransformer = productPictureTransformer;
        this.restaurantService = restaurantService;
    }

    public List<ProductResponse> list(Long restaurantId, boolean includeInactive) {
        var restaurant = restaurantService.verifyIfExists(restaurantId);

        var allProducts = includeInactive
                ? productRepository.findAllByRestaurant(restaurant)
                : productRepository.findAllActiveByRestaurant(restaurant);

        return productTransformer.toResponse(allProducts);
    }

    public ProductResponse find(Long restaurantId, Long productId) {
        var product = verifyIfExists(restaurantId, productId);

        return productTransformer.toResponse(product);
    }

    @Transactional
    public ProductResponse save(Long restaurantId, ProductRequest productRequest) {
        var restaurant = restaurantService.verifyIfExists(restaurantId);

        var product = productTransformer.toEntity(productRequest);
        product.setRestaurant(restaurant);

        return productTransformer.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long restaurantId, Long productId, ProductRequest productRequest) {
        var existingProduct = verifyIfExists(restaurantId, productId);

        productTransformer.copyPropertiesToEntity(productRequest, existingProduct);

        return productTransformer.toResponse(productRepository.save(existingProduct));
    }

    @Transactional
    public void updatePicture(Long restaurantId, Long productId, ProductPictureRequest request) {
        verifyIfExists(restaurantId, productId);

        var picture = productPictureTransformer.toEntity(request, productId);

        productRepository.save(picture);
    }

    public Product verifyIfExists(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId, restaurantId));
    }
}
