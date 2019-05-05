package com.sanluna.clients.memberclient.model;

import com.sanluna.commons.model.BaseDTO;
import com.sanluna.commons.model.entity.BaseEntity;

public class MemberDTO extends BaseDTO<MemberDTO> {

    private String username;
    private String password;
    private String roles;

    public String getUsername() {
        return username;
    }

    public MemberDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MemberDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public MemberDTO setRoles(String roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public <T1 extends BaseEntity<T1>> T1 convertToEntity() {
        return null;
    }
}
