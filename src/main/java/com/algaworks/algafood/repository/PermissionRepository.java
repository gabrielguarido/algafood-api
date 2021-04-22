package com.algaworks.algafood.repository;

import com.algaworks.algafood.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
