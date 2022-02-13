package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPicture;
import org.springframework.stereotype.Component;

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
}
