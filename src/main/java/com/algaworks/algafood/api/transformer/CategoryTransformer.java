package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.CategoryRequest;
import com.algaworks.algafood.api.model.CategoryResponse;
import com.algaworks.algafood.domain.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CategoryTransformer {

    private final ModelMapper mapper;

    @Autowired
    public CategoryTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CategoryResponse toResponse(Category category) {
        return mapper.map(category, CategoryResponse.class);
    }

    public List<CategoryResponse> toResponse(List<Category> categoryList) {
        return categoryList.stream()
                .map(this::toResponse)
                .collect(toList());
    }

    public Category toEntity(CategoryRequest categoryRequest) {
        return mapper.map(categoryRequest, Category.class);
    }

    public void copyPropertiesToEntity(CategoryRequest categoryRequest, Category category) {
        mapper.map(categoryRequest, category);
    }
}
