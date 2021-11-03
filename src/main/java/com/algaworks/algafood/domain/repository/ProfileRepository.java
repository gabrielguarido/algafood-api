package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
