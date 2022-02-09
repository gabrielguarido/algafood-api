package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByExternalKey(String externalKey);

    @Query("from Order o join fetch o.client join fetch o.restaurant r join fetch r.category")
    List<Order> findAll();
}
