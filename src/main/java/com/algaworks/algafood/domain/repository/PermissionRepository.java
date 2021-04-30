package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
