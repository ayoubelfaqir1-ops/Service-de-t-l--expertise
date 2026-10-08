package com.teleexpertise.security;

import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

public class CustomSecurityContext implements SecurityContext {

    private final AuthenticatedUser user;
    private final boolean secure;

    public CustomSecurityContext(
            AuthenticatedUser user,
            boolean secure
    ) {
        this.user = user;
        this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user.getRole() != null && user.getRole().equalsIgnoreCase(role);
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }
}