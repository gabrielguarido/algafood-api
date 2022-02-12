package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.ProductPictureRequest;
import com.algaworks.algafood.domain.model.ProductPicture;
import org.springframework.stereotype.Component;

@Component
public class ProductPictureTransformer {

    public ProductPicture toEntity(ProductPictureRequest request, Long productId) {
        return ProductPicture.builder()
                .productId(productId)
                .fileName(request.getFile().getOriginalFilename())
                .contentType(request.getFile().getContentType())
                .fileSize(request.getFile().getSize())
                .build();
    }
}
