package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByMethod(String method);
}
