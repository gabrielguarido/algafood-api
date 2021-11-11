package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {

    @Query("from Order o join fetch o.client join fetch o.restaurant r join fetch r.category")
    List<Order> findAll();
}
