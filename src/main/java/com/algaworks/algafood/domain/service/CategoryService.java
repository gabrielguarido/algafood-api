package com.algaworks.algafood.domain.service;

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

@Service
public class CategoryService {

    private static final String CATEGORY_IN_USE_EXCEPTION_MESSAGE = "The category ID %s is currently being used and cannot be removed";
    private static final String CATEGORY_TYPE_NOT_FOUND_EXCEPTION_MESSAGE = "Category type %s not found";

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public Category find(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public Category findByType(String type) {
        return categoryRepository.findByType(type).orElseThrow(() -> new CategoryNotFoundException(
                String.format(CATEGORY_TYPE_NOT_FOUND_EXCEPTION_MESSAGE, type)
        ));
    }

    public boolean existsByType(String type) {
        return categoryRepository.existsByType(type);
    }

    public Category findFirst() {
        return categoryRepository.findFirst().orElse(null);
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, Category category) {
        try {
            var existingCategory = find(id);

            BeanUtils.copyProperties(category, existingCategory, "id");

            return save(existingCategory);
        } catch (CategoryNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format(CATEGORY_IN_USE_EXCEPTION_MESSAGE, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new CategoryNotFoundException(id);
        }
    }
}
