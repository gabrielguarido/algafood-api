package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class AuthUser extends User {

    private static final long serialVerionUID = 1L;

    private String fullName;

    public AuthUser(com.algaworks.algafood.domain.model.User user) {
        super(user.getEmail(), user.getPassword(), Collections.emptyList());

        this.fullName = user.getName();
    }
}
