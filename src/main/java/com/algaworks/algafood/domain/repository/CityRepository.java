package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
