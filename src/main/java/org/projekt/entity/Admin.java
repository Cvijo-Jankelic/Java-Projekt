package org.projekt.entity;

import java.sql.Timestamp;

public class Admin extends AppUser{

    public Admin(Integer id, String username, String password, String role, Timestamp createdAt) {
        super(id, username, password, role, createdAt);
    }
}
