package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.query.RestaurantRepositoryQueries;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    List<Restaurant> queryByShippingTaxBetween(BigDecimal initialTax, BigDecimal finalTax);

    List<Restaurant> findByName(String name);

    Optional<Restaurant> findFirstByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByCategoryId(Long categoryId);
}
