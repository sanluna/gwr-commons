package com.sanluna.security.principal;

import java.io.Serializable;

/**
 * Security principal that contains user name and tenant.
 * NOTE This class is copied from relevate-starter-security.
 */
public class GWRPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String username;
    private final String roles;
    private final String tenant;


    public GWRPrincipal(String id, String username, String roles, String tenant) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.tenant = tenant;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTenant() {
        return tenant;
    }

    public String getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return String.format("%s", username);
    }
}
