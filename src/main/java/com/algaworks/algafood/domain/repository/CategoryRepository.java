package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CustomJpaRepository<Category, Long> {

    Optional<Category> findByType(String type);

    boolean existsByType(String type);
}
