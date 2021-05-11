package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ResourceInUseException;
import com.algaworks.algafood.domain.exception.ResourceNotFoundException;
import com.algaworks.algafood.domain.model.Category;
import com.algaworks.algafood.domain.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public Category find(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Category ID %s not found", id)
        ));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Category existingCategory = find(id);

        BeanUtils.copyProperties(category, existingCategory, "id");

        return save(existingCategory);
    }

    public void delete(Long id) {
        try {
            categoryRepository.delete(find(id));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceInUseException(
                    String.format("The category %s is currently being used and cannot be removed", id)
            );
        }
    }
}
