package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.request.ProductRequest;
import com.algaworks.algafood.api.model.response.ProductResponse;
import com.algaworks.algafood.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ProductTransformer {

    private final ModelMapper mapper;

    @Autowired
    public ProductTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProductResponse toResponse(Product product) {
        return mapper.map(product, ProductResponse.class);
    }

    public List<ProductResponse> toResponse(List<Product> productList) {
        return productList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public Product toEntity(ProductRequest productRequest) {
        return mapper.map(productRequest, Product.class);
    }

    public void copyPropertiesToEntity(ProductRequest productRequest, Product product) {
        mapper.map(productRequest, product);
    }
}
