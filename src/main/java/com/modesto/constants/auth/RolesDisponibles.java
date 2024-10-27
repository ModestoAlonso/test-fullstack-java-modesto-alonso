package com.modesto.constants.auth;

import org.springframework.security.core.GrantedAuthority;

public enum RolesDisponibles implements GrantedAuthority {
    ADMIN,
    CONSULTOR;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
