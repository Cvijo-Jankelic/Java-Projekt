package org.projekt.entity;

import org.projekt.Enum.Role;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Admin extends AppUser{

    public Admin(Integer id, String username, String password, Role role, LocalDateTime createdAt) {
        super(id, username, password, role, createdAt);
    }
}
