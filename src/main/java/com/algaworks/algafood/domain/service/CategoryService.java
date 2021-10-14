package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.model.CategoryRequest;
import com.algaworks.algafood.api.model.CategoryResponse;
import com.algaworks.algafood.api.transformer.CategoryTransformer;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CategoryNotFoundException;
import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final String CATEGORY_IN_USE_EXCEPTION_MESSAGE = "The category ID %s is currently being used and cannot be removed";
    private static final String CATEGORY_TYPE_NOT_FOUND_EXCEPTION_MESSAGE = "Category type %s not found";

    private final CategoryRepository categoryRepository;

    private final CategoryTransformer categoryTransformer;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryTransformer categoryTransformer) {
        this.categoryRepository = categoryRepository;
        this.categoryTransformer = categoryTransformer;
    }

    public List<CategoryResponse> list() {
        return categoryTransformer.toResponse(categoryRepository.findAll());
    }

    public CategoryResponse find(Long id) {
        return categoryTransformer.toResponse(verifyIfExists(id));
    }

    public CategoryResponse findByType(String type) {
        return categoryTransformer.toResponse(verifyIfExistsByType(type));
    }

    public boolean existsByType(String type) {
        return categoryRepository.existsByType(type);
    }

    public CategoryResponse findFirst() {
        Optional<Category> category = categoryRepository.findFirst();

        if (category.isEmpty()) {
            return null;
        }

        return categoryTransformer.toResponse(category.get());
    }

    @Transactional
    public CategoryResponse save(CategoryRequest categoryRequest) {
        var category = categoryTransformer.toEntity(categoryRequest);

        return categoryTransformer.toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        try {
            var category = categoryTransformer.toEntity(categoryRequest);

            var existingCategory = verifyIfExists(id);

            BeanUtils.copyProperties(category, existingCategory, "id");

            return categoryTransformer.toResponse(categoryRepository.save(existingCategory));
        } catch (CategoryNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            categoryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(CATEGORY_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new CategoryNotFoundException(id);
        }
    }

    private Category verifyIfExists(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    private Category verifyIfExistsByType(String type) {
        return categoryRepository.findByType(type).orElseThrow(() -> new CategoryNotFoundException(
                String.format(CATEGORY_TYPE_NOT_FOUND_EXCEPTION_MESSAGE, type)
        ));
    }
}
