package org.projekt.entity;

import org.projekt.Enum.Role;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommonUser extends AppUser {

    public CommonUser(Integer id, String username, String password, Role role, LocalDateTime createdAt) {
        super(id, username, password, role, createdAt);
    }


    @Override
    public String toString() {
        return super.getUsername();
    }
}
