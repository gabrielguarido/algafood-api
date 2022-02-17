package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductPictureResponse;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.api.transformer.ProductPictureTransformer;
import com.algaworks.algafood.api.transformer.ProductTransformer;
import com.algaworks.algafood.domain.exception.ProductNotFoundException;
import com.algaworks.algafood.domain.exception.ProductPictureNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPicture;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.storage.PictureStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductTransformer productTransformer;

    private final ProductPictureTransformer productPictureTransformer;

    private final RestaurantService restaurantService;

    private final PictureStorageService pictureStorageService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductTransformer productTransformer,
                          RestaurantService restaurantService, ProductPictureTransformer productPictureTransformer,
                          PictureStorageService pictureStorageService) {
        this.productRepository = productRepository;
        this.productTransformer = productTransformer;
        this.productPictureTransformer = productPictureTransformer;
        this.restaurantService = restaurantService;
        this.pictureStorageService = pictureStorageService;
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
    public void updatePicture(Long restaurantId, Long productId, ProductPictureRequest request) throws IOException {
        var existingPicture = productRepository.findPictureById(restaurantId, productId);

        var fileName = pictureStorageService.generateFileName(request.getFile().getOriginalFilename());

        persistPicture(existingPicture, request, fileName, restaurantId, productId);

        storePicture(existingPicture, request, fileName);
    }

    private void persistPicture(Optional<ProductPicture> existingPicture, ProductPictureRequest request, String fileName,
                                Long restaurantId, Long productId) {
        existingPicture.ifPresent(productRepository::delete);

        var product = verifyIfExists(restaurantId, productId);

        var newPicture = productPictureTransformer.toEntity(request, product, fileName);

        productRepository.save(newPicture);
        productRepository.flush();
    }

    private void storePicture(Optional<ProductPicture> existingPicture, ProductPictureRequest request, String fileName) throws IOException {
        existingPicture.ifPresent(picture -> pictureStorageService.remove(picture.getFileName()));

        var picture = PictureStorageService.Picture.builder()
                .fileName(fileName)
                .contentType(request.getFile().getContentType())
                .inputStream(request.getFile().getInputStream())
                .build();

        pictureStorageService.store(picture);
    }

    public ProductPictureResponse retrievePicture(Long restaurantId, Long productId) {
        var productPicture = productRepository.findPictureById(restaurantId, productId);

        if (productPicture.isEmpty()) {
            throw new ProductPictureNotFoundException(productId);
        }

        MediaType mediaType = MediaType.parseMediaType(productPicture.get().getContentType());

        PictureStorageService.PictureResponse pictureResponse = pictureStorageService.retrieve(productPicture.get().getFileName());

        return productPictureTransformer.toResponse(mediaType, pictureResponse.getInputStream(), pictureResponse.getUrl());
    }

    public void deletePicture(Long restaurantId, Long productId) {
        verifyIfExists(restaurantId, productId);

        var existingPicture = productRepository.findPictureById(restaurantId, productId);

        if (existingPicture.isPresent()) {
            productRepository.delete(existingPicture.get());
            productRepository.flush();

            pictureStorageService.remove(existingPicture.get().getFileName());
        }
    }

    public Product verifyIfExists(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId, restaurantId));
    }
}
