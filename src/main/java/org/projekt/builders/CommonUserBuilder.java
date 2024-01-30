package org.projekt.builders;

import org.projekt.Enum.Role;
import org.projekt.entity.CommonUser;

import java.time.LocalDateTime;

public class CommonUserBuilder {
    private Integer id;
    private String username;
    private String password;
    private Role role;
    private LocalDateTime createdAt;

    public CommonUserBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public CommonUserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public CommonUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public CommonUserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public CommonUserBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CommonUser createCommonUser() {
        return new CommonUser(id, username, password, role, createdAt);
    }
}