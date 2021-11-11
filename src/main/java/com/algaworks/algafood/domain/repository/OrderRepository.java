package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {
}
