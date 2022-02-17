package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.api.model.response.ProductPictureResponse;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPicture;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class ProductPictureTransformer {

    public ProductPicture toEntity(ProductPictureRequest request, Product product, String fileName) {
        return ProductPicture.builder()
                .productId(product.getId())
                .product(product)
                .fileName(fileName)
                .contentType(request.getFile().getContentType())
                .fileSize(request.getFile().getSize())
                .build();
    }

    public ProductPictureResponse toResponse(MediaType mediaType, InputStream inputStream, String url) {
        return ProductPictureResponse.builder()
                .mediaType(mediaType)
                .inputStream(inputStream)
                .url(url)
                .build();
    }
}
