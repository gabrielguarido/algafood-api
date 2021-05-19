package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
