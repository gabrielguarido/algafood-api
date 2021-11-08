package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PermissionNotFoundException;
import com.algaworks.algafood.domain.model.Permission;
import com.algaworks.algafood.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission verifyIfExists(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new PermissionNotFoundException(id));
    }
}
