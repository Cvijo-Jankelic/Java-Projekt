package org.projekt.entity;

import java.sql.Timestamp;

public class CommonUser extends AppUser {

    public CommonUser(Integer id, String username, String password, String role, Timestamp createdAt) {
        super(id, username, password, role, createdAt);
    }
}
