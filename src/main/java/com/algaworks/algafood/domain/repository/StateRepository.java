package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByName(String name);
}
