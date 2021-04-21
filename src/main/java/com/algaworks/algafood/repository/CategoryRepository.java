package com.algaworks.algafood.repository;

import com.algaworks.algafood.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
