package com.ecommerceproject.Entity;

import java.io.Serializable;
import java.util.Objects;

public class RoleId implements Serializable {
    private String user; // Matches the User entity's ID type
    private String role;

    public RoleId() {}

    public RoleId(String user, String role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleId that = (RoleId) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}