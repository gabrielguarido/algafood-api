package com.algaworks.algafood.domain.model;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = 1L;

    private final Long userId;
    private final String fullName;

    public AuthUser(com.algaworks.algafood.domain.model.User user) {
        super(user.getEmail(), user.getPassword(), Collections.emptyList());

        this.userId = user.getId();
        this.fullName = user.getName();
    }
}
