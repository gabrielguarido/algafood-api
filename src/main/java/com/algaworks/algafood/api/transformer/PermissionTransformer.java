package com.algaworks.algafood.api.transformer;

import com.algaworks.algafood.api.model.response.PermissionResponse;
import com.algaworks.algafood.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PermissionTransformer {

    private final ModelMapper mapper;

    @Autowired
    public PermissionTransformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PermissionResponse toResponse(Permission permission) {
        return mapper.map(permission, PermissionResponse.class);
    }

    public List<PermissionResponse> toResponse(Collection<Permission> permissionList) {
        return permissionList.stream()
                .map(this::toResponse)
                .collect(toList());
    }
}
