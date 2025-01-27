package com.ecommerceproject.Dto;

public class AuthRequest {
    private String id;
    private String password;

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.id = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
