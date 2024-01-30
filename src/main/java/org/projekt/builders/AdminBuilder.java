package org.projekt.builders;

import org.projekt.Enum.Role;
import org.projekt.entity.Admin;

import java.time.LocalDateTime;

public class AdminBuilder {
    private Integer id;
    private String username;
    private String password;
    private Role role;
    private LocalDateTime createdAt;

    public AdminBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public AdminBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public AdminBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public AdminBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Admin createAdmin() {
        return new Admin(id, username, password, role, createdAt);
    }
}