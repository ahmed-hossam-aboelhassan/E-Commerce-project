package com.ecommerceproject.Entity;

import jakarta.persistence.*;

@Entity
@IdClass(RoleId.class)
@Table(name="role")
public class Role {

    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @Column(name="role")
    private String role;

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "user='" + user + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
