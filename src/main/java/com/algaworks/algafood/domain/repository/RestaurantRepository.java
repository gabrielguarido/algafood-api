package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.query.RestaurantQueryRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        RestaurantQueryRepository, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.category left join fetch r.paymentMethods")
    List<Restaurant> findAll();

    List<Restaurant> queryByDeliveryFeeBetween(BigDecimal initialFee, BigDecimal finalFee);

    List<Restaurant> findByName(String name);

    Optional<Restaurant> findFirstByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByCategoryId(Long categoryId);
}
